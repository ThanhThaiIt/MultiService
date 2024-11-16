package com.example.multiservice.cronjob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.multiservice.service.InvalidateTokenService;

@Component
public class CleanupScheduler {

    @Autowired
    private InvalidateTokenService invalidateTokenService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleTokenCleanup() {
        invalidateTokenService.deleteExpiredTokens();
    }
}
