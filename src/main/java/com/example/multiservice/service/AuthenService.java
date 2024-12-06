package com.example.multiservice.service;

import com.example.multiservice.dto.request.*;
import com.example.multiservice.dto.response.RegisterResponse;
import org.springframework.stereotype.Service;

import com.example.multiservice.dto.response.AuthenticationResponse;
import com.example.multiservice.dto.response.IntrospectResponse;

@Service
public interface AuthenService {

    AuthenticationResponse Authenticate(AuthenticationRequest authenticationRequest);

    IntrospectResponse introspect(IntrospectRequest introspectRequest);

    void logout(LogoutRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    RegisterResponse register(RegisterRequest registerRequest);

    String activeAcc(String token);

    String resendEmail(String email);

}
