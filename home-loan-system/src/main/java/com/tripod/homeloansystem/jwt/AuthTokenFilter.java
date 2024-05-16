package com.tripod.homeloansystem.jwt;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    private static Logger logger = (Logger) LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("Filtering request for URI: {}", request.getRequestURI());

        try{
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateToken(jwt)){
                String username = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                logger.info("Roles for JWT: {}", userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch(Exception e){
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String jwt = jwtUtils.getJwtFromHeader(request);
        logger.info("AuthTokenFilter.java : {}", jwt);
        return jwt;
    }
    
}


/*
 * 
Sure, let's imagine you're at a fun playground. This playground has a special rule: to play on the equipment, you need a special sticker. This sticker proves that you're allowed to play there.

The AuthTokenFilter class is like the playground supervisor who checks your sticker.

When you come to play (doFilterInternal method), the supervisor (AuthTokenFilter) checks if you have a sticker (jwt token).
The supervisor uses a special tool (jwtUtils) to check if the sticker is valid (validateToken method).
If the sticker is valid, the supervisor checks the name on the sticker (getUsernameFromToken method) and looks you up in the list of kids allowed to play (loadUserByUsername method).
If you're on the list, the supervisor gives you a special badge (UsernamePasswordAuthenticationToken) that lets you play on the equipment. This badge also shows what equipment you're allowed to play on (getAuthorities method).
The supervisor then tells everyone that you're allowed to play there (setAuthentication method).
If something goes wrong (like if the sticker is missing or invalid), the supervisor logs an error message (logger.error method).
Finally, whether you have a sticker or not, you're allowed to move on to the playground (filterChain.doFilter method).
The parseJwt method is like the supervisor's helper who actually looks at your sticker and reads the information on it (getJwtFromHeader method).

That's the basic idea of the AuthTokenFilter class! It checks if you have a valid JWT token (sticker) and sets up your authentication (gives you a badge) so you can access the protected resources (play on the equipment).
* 
*/