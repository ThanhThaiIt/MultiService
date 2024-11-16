package com.example.multiservice.dto.request;

import lombok.Builder;

@Builder
public record IntrospectRequest(String token) {}
