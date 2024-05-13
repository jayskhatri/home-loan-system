package com.tripod.homeloansystem.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    Logger logger = (Logger) LoggerFactory.getLogger(JwtUtils.class);

    @Value("${security.jwt.token.secret-key}")
    private String jwtSecret;

    @Value("${security.jwt.token.expiration}")
    private int jwtExpirationMs;


    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        logger.info("Bearer Token: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateTokenFromUsername(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){
        try {
            System.out.println("Validating Token: " + token);
            Jwts.parser().verifyWith((SecretKey)key()).build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e){
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            logger.error("JWT token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
     
}
