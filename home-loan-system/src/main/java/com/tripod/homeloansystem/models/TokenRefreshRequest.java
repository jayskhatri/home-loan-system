package com.tripod.homeloansystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshRequest {
    
    @NonNull
    private String refreshToken;

}
