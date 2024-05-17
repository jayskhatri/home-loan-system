package com.tripod.homeloansystem.jwt;

import java.util.List;

import com.tripod.homeloansystem.models.RefreshToken;

import lombok.Data;

@Data
public class LoginResponse {
    private String jwtToken;
    private Long id;
    private String username;
    private String type = "Bearer";
	private RefreshToken refreshToken;
    private List<String> roles;

    public LoginResponse(String jwtToken, Long id, String username, RefreshToken refreshToken, List<String> roles) {
        this.jwtToken = jwtToken;
        this.id = id;
        this.username = username;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }
}
