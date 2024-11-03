package com.example.multiservice.utils;

import com.example.multiservice.dto.request.IntrospectRequest;
import com.example.multiservice.dto.response.IntrospectResponse;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor

 public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);
    long EXPIRATION_TIME =15 * 60 * 1000; //1 p

    @NonFinal// marking to do not let Inject vào Contructor
    @Value("${jwt.secret.key}")
    protected   String secrect;

    public String generateToken(String username) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);// Type Of Algorithm
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("multiservice.com")// who issue
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
                .claim("Hello User Đã Đúng","Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(secrect.getBytes()));
            return jwsObject.serialize();// serialize into string
        } catch (JOSEException e) {
            log.error("Can not create JWT object", e);
            throw new RuntimeException(e);
        }

    }

    public boolean validationToken(String  token) throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(secrect.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);


        Date isExpired = signedJWT.getJWTClaimsSet().getExpirationTime();// get expire time from token return true false

        if (!isExpired.after(new Date())) {
            throw new AppException(ErrorStatusCode.TOKEN_EXPIRED);
        }

        // available function
        var verified = signedJWT.verify(verifier);// return true or false: true if is correct token  and vice versa

        return verified && isExpired.after(new Date());
    }



}
