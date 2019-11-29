package com.netreaders.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationToken {

    private static final int EXPIRATION = 60 * 24;
    private int tokenId;
    private String token;
    private int userId;
    private LocalDateTime createdDateTime;

    public LocalDateTime calculateExpiryDate() {
        return createdDateTime.plusMinutes(EXPIRATION);
    }
}
