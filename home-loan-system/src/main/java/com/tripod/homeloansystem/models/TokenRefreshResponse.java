package com.tripod.homeloansystem.models;

import java.time.Instant;

import lombok.Data;

@Data
public class TokenRefreshResponse {
    private String jwtToken;
    private String refreshToken;
    private Instant expiresIn;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String jwtToken, String refreshToken, Instant expiresIn) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }
}
