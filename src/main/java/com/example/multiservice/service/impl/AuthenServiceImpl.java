package com.example.multiservice.service.impl;

import com.example.multiservice.dto.request.AuthenticationRequest;
import com.example.multiservice.dto.request.IntrospectRequest;
import com.example.multiservice.dto.response.AuthenticationResponse;
import com.example.multiservice.dto.response.IntrospectResponse;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.example.multiservice.repository.UserRepository;
import com.example.multiservice.service.AuthenService;
import com.example.multiservice.utils.JwtUtils;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenServiceImpl implements AuthenService {

    UserRepository userRepository;
    JwtUtils jwtUtils;


    @Override
    public AuthenticationResponse Authenticate(AuthenticationRequest authenticationRequest) {
        var userEntity = userRepository.findByEmail(authenticationRequest.email()).orElseThrow(() -> new  AppException(ErrorStatusCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean checkAuthen =passwordEncoder.matches(authenticationRequest.password(), userEntity.getPassword_hash());
        if(!checkAuthen){
            throw new AppException(ErrorStatusCode.UNAUTHENTICATED);
        }
        var token = jwtUtils.generateToken(userEntity.getEmail());

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {

        //var token  = introspectRequest.token();
        var check = false;
        try {
              check =jwtUtils.validationToken(introspectRequest.token());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new AppException(ErrorStatusCode.IN_CORRECT_FORMAT_JWT);// in correct format jwt
        }

        return IntrospectResponse.builder()
                .valid(check)
                .build();
    }


}
