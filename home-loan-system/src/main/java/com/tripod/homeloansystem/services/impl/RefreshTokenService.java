package com.tripod.homeloansystem.services.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripod.homeloansystem.exceptions.TokenRefreshException;
import com.tripod.homeloansystem.models.Person;
import com.tripod.homeloansystem.models.RefreshToken;
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

    // Find a refresh token by its token value
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    // Create a new refresh token for a user
    public RefreshToken createRefreshToken(Long userId) {
        // Find the user
        Person user = personRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        // Find the existing refresh token
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUser(user);
        RefreshToken refreshToken;
        if (refreshTokenOptional.isPresent()) {
            // Update the existing refresh token
            refreshToken = refreshTokenOptional.get();
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
        } else {
            // Create a new refresh token
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
        }

        // Save the refresh token
        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    // Verify if a refresh token has expired
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    // Delete a refresh token by user ID
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(personRepository.findById(userId).get());
    }
}
