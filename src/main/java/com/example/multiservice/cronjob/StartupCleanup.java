package com.example.multiservice.cronjob;

import com.example.multiservice.service.InvalidateTokenService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartupCleanup {


    @Autowired
    private InvalidateTokenService invalidateTokenService;

    @PostConstruct
    public void cleanupOnStartup() {
        invalidateTokenService.deleteExpiredTokens();
    }
}
