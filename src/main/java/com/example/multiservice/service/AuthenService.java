package com.example.multiservice.service;


import com.example.multiservice.dto.request.AuthenticationRequest;
import com.example.multiservice.dto.request.IntrospectRequest;
import com.example.multiservice.dto.request.LogoutRequest;
import com.example.multiservice.dto.request.RefreshTokenRequest;
import com.example.multiservice.dto.response.AuthenticationResponse;
import com.example.multiservice.dto.response.IntrospectResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenService {

    AuthenticationResponse Authenticate(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspect(IntrospectRequest introspectRequest);
    void logout(LogoutRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
