package com.example.multiservice.service.impl;

import com.example.multiservice.dto.UserWithRolesDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenServiceImpl implements AuthenService {

    UserRepository userRepository;
    JwtUtils jwtUtils;
    //UserRoleRepository userRoleRepository;


    @Override
    public AuthenticationResponse Authenticate(AuthenticationRequest authenticationRequest) {
        //var authentication = SecurityContextHolder.getContext().getAuthentication();
        //log.info("Username: {}", authentication.getName());
        var userEntity = userRepository.findByEmail(authenticationRequest.email()).orElseThrow(() -> new  AppException(ErrorStatusCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean checkAuthen =passwordEncoder.matches(authenticationRequest.password(), userEntity.getPassword_hash());
        if(!checkAuthen){
            throw new AppException(ErrorStatusCode.UNAUTHENTICATED);
        }

//        List<Object[]> user = userRepository.findUserWithRolesNative(userEntity.getId());
//        for (Object[] a : user){
//            System.out.println((String)a[0]+a[1]);
//        }



        var token = jwtUtils.generateToken(userEntity);

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
