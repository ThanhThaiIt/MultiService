package com.example.multiservice.cronjob;

import com.example.multiservice.service.InvalidateTokenService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "spring",value = "datasource.driver-class-name",havingValue = "com.mysql.cj.jdbc.Driver")
public class StartupCleanup {


    private static final Logger log = LoggerFactory.getLogger(StartupCleanup.class);
    @Autowired
    private InvalidateTokenService invalidateTokenService;

    @PostConstruct
    public void cleanupOnStartup() {
        log.info(" Running in StartupCleanup");
        invalidateTokenService.deleteExpiredTokens();
    }
}
