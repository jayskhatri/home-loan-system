package com.tripod.homeloansystem.models;

import lombok.Data;

@Data
public class TokenRefreshResponse {
    private String jwtToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String jwtToken, String refreshToken) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }
}
