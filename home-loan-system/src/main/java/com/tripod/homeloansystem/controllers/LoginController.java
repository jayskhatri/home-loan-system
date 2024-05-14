package com.tripod.homeloansystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripod.homeloansystem.exceptions.TokenRefreshException;
import com.tripod.homeloansystem.jwt.JwtUtils;
import com.tripod.homeloansystem.jwt.LoginRequest;
import com.tripod.homeloansystem.jwt.LoginResponse;
import com.tripod.homeloansystem.jwt.RefreshToken;
import com.tripod.homeloansystem.models.Person;
import com.tripod.homeloansystem.models.TokenRefreshRequest;
import com.tripod.homeloansystem.models.TokenRefreshResponse;
import com.tripod.homeloansystem.services.impl.PersonServiceImpl;
import com.tripod.homeloansystem.services.impl.RefreshTokenService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonServiceImpl personService;

    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        System.out.println("Login Request: " + loginRequest);
        
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        }catch(AuthenticationException e){
            System.out.println("Sherin on the meet");
            return ResponseEntity.badRequest().body("Invalid username or password!");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Person userDetails = (Person) authentication.getPrincipal();
        String jwt = jwtUtils.generateTokenFromUsername(userDetails);
        
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getPersonId());

        Person person = personService.getPersonByUsername(loginRequest.getUsername());
        LoginResponse loginResponse;
        
        if(person!=null){
            if (person.getIsAdmin()) {
                loginResponse = new LoginResponse(jwt, userDetails.getPersonId(), userDetails.getUsername(), refreshToken, List.of("ADMIN"));
            } else {
                loginResponse = new LoginResponse(jwt, userDetails.getPersonId(), userDetails.getUsername(), refreshToken, List.of("USER"));
            }
            return ResponseEntity.ok(loginResponse);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        System.out.println("Refresh Token Request: " + request);
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String token = jwtUtils.generateTokenFromUsername(user);
                refreshTokenService.deleteByUserId(user.getPersonId());
                RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getPersonId());
                return ResponseEntity.ok(new TokenRefreshResponse(token, newRefreshToken.getToken()));
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                "Refresh token is not in database!"));
    }

    @GetMapping("/login")
    public String loginUser(){
        return "User logged in";
    }

    @GetMapping("/logout")
    public String logoutUser(){
        return "User logged out";
    }

}
