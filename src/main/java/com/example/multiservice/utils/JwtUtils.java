package com.example.multiservice.utils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.example.multiservice.entity.PermissionEntity;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.example.multiservice.repository.InvalidateTokenRepository;
import com.example.multiservice.repository.PermissionRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @NonFinal // marking to do not let Inject vào Contructor
    @Value("${jwt.expiration-time}")
    long EXPIRATION_TIME; // 1 p

    @NonFinal // marking to do not let Inject vào Contructor
    @Value("${jwt.secret.key}")
    protected String SECRET_KEY;

    @NonFinal // marking to do not let Inject vào Contructor
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    PermissionRepository permissionRepository;

    InvalidateTokenRepository invalidateTokenRepository;

    public String generateToken(UserEntity userEntity) {

        log.info("token generation started " + SECRET_KEY);

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512); // Type Of Algorithm
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userEntity.getEmail())
                .issuer("multiservice.com") // who issue
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(EXPIRATION_TIME, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString()) // 32 character randomly
                .claim("scope", buildScopes(userEntity))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize(); // serialize into string
        } catch (JOSEException e) {
            log.error("Can not create JWT object", e);
            throw new RuntimeException(e);
        }
    }

    // purpose: will return "SignedJWT" if token is valid,
    // signature(digital signature) is valid,
    // not in invalidatetoken table (token is logged out)
    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {

        // Initialize JWSVerifier to verify signature with secret key
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        // Parse the token into a SignedJWT object
        SignedJWT signedJWT = SignedJWT.parse(token);

        // get expire time from token return true false
        Date expirationTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        if (expirationTime != null && expirationTime.before(new Date()) && isRefresh) {
            throw new AppException(ErrorStatusCode.JWT_EXPIRED_REFRESH);
        }

        if (expirationTime != null && expirationTime.before(new Date())) {
            throw new AppException(ErrorStatusCode.TOKEN_EXPIRED);
        }

        // available function
        // return true or false: true if is correct token  and vice versa
        boolean verified = signedJWT.verify(verifier);
        if (!verified) {
            throw new AppException(ErrorStatusCode.UNAUTHENTICATED);
        }

        if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()) && isRefresh) {
            throw new AppException(ErrorStatusCode.JWT_LOGOUT_REFRESH);
        }

        if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorStatusCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    // The business here is that we will have a parameter as a UserEntity and in this entity there will be 1
    // ListRoles and each Role will have many Permissions, we will go through each element  to concatenate
    // the string and distinguish between Role and Permission as "ROLE_"
    private String buildScopes(UserEntity userEntity) {
        StringJoiner scopes = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(userEntity.getRoles())) {
            userEntity.getRoles().forEach(roleEntity -> {
                scopes.add("ROLE_" + roleEntity.getTitle());

                var permissions = getPermissions(roleEntity.getId());
                if (!CollectionUtils.isEmpty(permissions)) {
                    permissions.forEach(permissionEntity -> {
                        scopes.add(permissionEntity.getSlug());
                    });
                }
            });
        }
        return scopes.toString();
    }

    private List<PermissionEntity> getPermissions(int roleId) {

        List<PermissionEntity> permissionEntities = permissionRepository.findPermissionsByRoleId(roleId);

        return permissionEntities;
    }
}
