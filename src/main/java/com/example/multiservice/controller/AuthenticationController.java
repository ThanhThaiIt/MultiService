package com.example.multiservice.controller;

import com.example.multiservice.dto.request.*;
import com.example.multiservice.dto.response.RegisterResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.example.multiservice.dto.response.ApiResponse;
import com.example.multiservice.dto.response.AuthenticationResponse;
import com.example.multiservice.dto.response.IntrospectResponse;
import com.example.multiservice.service.AuthenService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenService authenService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenService.Authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest introspectRequest) {
        var result = authenService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        authenService.logout(logoutRequest);

        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request) {
        var rs = authenService.refreshToken(request);

        return ApiResponse.<AuthenticationResponse>builder().result(rs).build();
    }

    @PostMapping("/register")
    ApiResponse<RegisterResponse> register(@Valid RegisterRequest registerRequest) {

        var rs = authenService.register(registerRequest);

        return ApiResponse.<RegisterResponse>builder().result(rs).build();

    }

    @PostMapping("/resend-email")
    ApiResponse<String> resendEmail(@RequestParam String Data){
        var rs = authenService.resendEmail(Data);
        return ApiResponse.<String>builder().result(rs).build();

    }

    @GetMapping("/{token}")
    ApiResponse<String> activeAccount(@PathVariable("token") String token) {

        System.out.println("token is: " + token);
        var rs = authenService.activeAcc(token);
        return ApiResponse.<String>builder().result(rs).build();
    }
}
