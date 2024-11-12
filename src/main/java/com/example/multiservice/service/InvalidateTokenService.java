package com.example.multiservice.service;

import org.springframework.stereotype.Service;

@Service
public interface InvalidateTokenService {

    void deleteExpiredTokens();
}
