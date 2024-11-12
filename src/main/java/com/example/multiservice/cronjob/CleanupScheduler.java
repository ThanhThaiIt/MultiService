package com.example.multiservice.cronjob;

import com.example.multiservice.service.InvalidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupScheduler {

    @Autowired
    private InvalidateTokenService invalidateTokenService;


    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleTokenCleanup() {
        invalidateTokenService.deleteExpiredTokens();
    }

}
