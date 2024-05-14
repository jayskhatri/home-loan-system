package com.tripod.homeloansystem.services.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripod.homeloansystem.exceptions.TokenRefreshException;
import com.tripod.homeloansystem.jwt.RefreshToken;
import com.tripod.homeloansystem.repositories.PersonRepository;
import com.tripod.homeloansystem.repositories.RefreshTokenRepository;

@Service
public class RefreshTokenService {
    
    @Value("${security.jwt.refreshToken.expiration}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PersonRepository personRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        System.out.println(Instant.now() + "Instant ||| milliseconds " + refreshTokenDurationMs);
        refreshToken.setUser(personRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(personRepository.findById(userId).get());
    }


}
