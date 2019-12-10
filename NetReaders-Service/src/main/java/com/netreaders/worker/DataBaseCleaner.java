package com.netreaders.worker;

import com.netreaders.models.RegistrationToken;
import com.netreaders.service.RegistrationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
@Log4j
@AllArgsConstructor
public class DataBaseCleaner {

    final private static String SCHEDULED_TIME = "0 0 4 * * *";

    private final RegistrationTokenService registrationTokenService;

    @Scheduled(cron = SCHEDULED_TIME)
    public void cleanRegistrationLinkTable() {

        log.info("Starting to clean the registration_link table...");
        Collection<RegistrationToken> tokens = registrationTokenService.getAllTokens();

        tokens.stream()
                .filter(token -> LocalDateTime.now().isAfter(token.calculateExpiryDate()))
                .forEach(token -> registrationTokenService.removeToken(token.getToken()));
        log.info("Finish to clean the registration_link table!");
    }
}
