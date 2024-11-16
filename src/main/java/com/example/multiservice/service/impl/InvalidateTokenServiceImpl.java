package com.example.multiservice.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.multiservice.repository.InvalidateTokenRepository;
import com.example.multiservice.service.InvalidateTokenService;

@Service
public class InvalidateTokenServiceImpl implements InvalidateTokenService {

    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;

    @Override
    public void deleteExpiredTokens() {
        invalidateTokenRepository.deleteExpiredTokens(new Date());
    }
}
