package com.example.multiservice.service.impl;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.multiservice.configuration.RabbitmqConfig;
import com.example.multiservice.dto.request.*;
import com.example.multiservice.dto.response.RegisterResponse;
import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.mapper.UserMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.multiservice.dto.response.AuthenticationResponse;
import com.example.multiservice.dto.response.IntrospectResponse;
import com.example.multiservice.entity.InvalidateToken;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.example.multiservice.repository.InvalidateTokenRepository;
import com.example.multiservice.repository.UserRepository;
import com.example.multiservice.service.AuthenService;
import com.example.multiservice.utils.JwtUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenServiceImpl implements AuthenService {

    UserRepository userRepository;
    JwtUtils jwtUtils;
    InvalidateTokenRepository tokenRepository;
     UserMapper userMapper;
      RabbitTemplate rabbitTemplate;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse Authenticate(AuthenticationRequest authenticationRequest) {
        // var authentication = SecurityContextHolder.getContext().getAuthentication();
        // log.info("Username: {}", authentication.getName());
        var userEntity = userRepository
                .findByEmail(authenticationRequest.email())
                .orElseThrow(() -> new AppException(ErrorStatusCode.USER_NOT_FOUND));



        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean checkAuthen = passwordEncoder.matches(authenticationRequest.password(), userEntity.getPassword_hash());
        if (!checkAuthen) {
            throw new AppException(ErrorStatusCode.UNAUTHENTICATED);
        }

        if (!userEntity.isActive() && checkAuthen){
            log.info("User Correct But None Activate");

            rabbitTemplate.convertAndSend(RabbitmqConfig.NOTIFICATION_EXCHANGE,RabbitmqConfig.NOTIFICATION_ROUTING_KEY,userEntity.getEmail());
            return AuthenticationResponse.builder().statusActive("Email Sending").build();
        }

        var token = jwtUtils.generateToken(userEntity);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {

        boolean isValid = true;
        try {

            jwtUtils.verifyToken(introspectRequest.token(), false);

        } catch (AppException appException) {
            isValid = false;
        } catch (JOSEException e) {
            throw new AppException(ErrorStatusCode.JWT_VERIFICATION_FAILED);
        } catch (ParseException e) {
            throw new AppException(ErrorStatusCode.IN_CORRECT_FORMAT_JWT); // in correct format jwt
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public void logout(LogoutRequest request) {

        try {
            SignedJWT signedJWT = jwtUtils.verifyToken(request.token(), true);
            String jwtTokenId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .id(jwtTokenId)
                    .expiryDate(expirationDate)
                    .build();

            tokenRepository.save(invalidateToken);

        } catch (JOSEException e) {
            throw new AppException(ErrorStatusCode.JWT_VERIFICATION_FAILED);
        } catch (ParseException e) {
            throw new AppException(ErrorStatusCode.IN_CORRECT_FORMAT_JWT); // in correct format jwt
        } catch (AppException appException) {
            log.info("Token already expired");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        var token = "";
        try {
            SignedJWT signedJWT = jwtUtils.verifyToken(refreshTokenRequest.token(), true);
            String jwtTokenId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .id(jwtTokenId)
                    .expiryDate(expirationDate)
                    .build();

            tokenRepository.save(invalidateToken);

            var emailUser = signedJWT.getJWTClaimsSet().getSubject();

            UserEntity userEntity = userRepository
                    .findByEmail(emailUser)
                    .orElseThrow(() -> new AppException(ErrorStatusCode.USER_NOT_FOUND));
            token = jwtUtils.generateToken(userEntity);

        } catch (JOSEException e) {
            throw new AppException(ErrorStatusCode.JWT_VERIFICATION_FAILED);
        } catch (ParseException e) {
            throw new AppException(ErrorStatusCode.IN_CORRECT_FORMAT_JWT); // in correct format jwt
        }

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        UserEntity userEntity = userMapper.toRegisterUser(registerRequest);
        userEntity.setPassword_hash(passwordEncoder.encode(registerRequest.password_hash()));
        userEntity.setAvatar_url(registerRequest.avatar_url().getOriginalFilename());
        userEntity.setRegistered_at(LocalDateTime.now());
        userEntity.setLast_login(LocalDateTime.now());
        userEntity.setActive(0);
        List<RoleEntity> roles = Collections.singletonList(RoleEntity.builder().id(2).build());
        userEntity.setRoles(roles);
        UserEntity user = userRepository.save(userEntity);




        String token = jwtUtils.generateToken(user);
        String message =  user.getEmail()+" "+token;

        // Sent Data To Rabbit Mq
        rabbitTemplate.convertAndSend(RabbitmqConfig.ACTIVATE_ACCOUNT_EXCHANGE,RabbitmqConfig.ACTIVATE_ACCOUNT_ROUTING_KEY, message);


        return RegisterResponse.builder().token(token).build();
    }

    @Override
    public String resendEmail(String email) {

        var user = userRepository.findByEmail(email);
        if (user != null) {
            String token = jwtUtils.generateToken(user.get());
            String message =  email+" "+token;

            // Sent Data To Rabbit Mq
            rabbitTemplate.convertAndSend(RabbitmqConfig.ACTIVATE_ACCOUNT_EXCHANGE,RabbitmqConfig.ACTIVATE_ACCOUNT_ROUTING_KEY, message);
            return message+" Resending Success";
        }

        return "Fail Resending Email";
    }

    @Override
    public String activeAcc(String token) {
        try {
            System.out.println("chuan bi veirfy");
            SignedJWT signedJWT = jwtUtils.verifyToken(token,false);
            String email = signedJWT.getJWTClaimsSet().getSubject();
            userRepository.findByEmail(email).ifPresent(user -> {
                user.setActive(1);
                userRepository.save(user);
            });
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return "Account Activated";
    }


}
