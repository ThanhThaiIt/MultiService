package com.example.multiservice.configuration;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.multiservice.dto.response.ApiResponse;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        ErrorStatusCode errorStatusCode = ErrorStatusCode.UNAUTHENTICATED;
        response.setStatus(errorStatusCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // ContentType

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorStatusCode.getCode())
                .message(errorStatusCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse)); // parse to String
        response.flushBuffer();
    }
}
