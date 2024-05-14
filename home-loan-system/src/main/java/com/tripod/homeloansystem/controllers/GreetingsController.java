package com.tripod.homeloansystem.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tripod.homeloansystem.jwt.JwtUtils;
import com.tripod.homeloansystem.jwt.LoginRequest;
import com.tripod.homeloansystem.jwt.LoginResponse;
import com.tripod.homeloansystem.models.Person;

@RestController
public class GreetingsController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String greetUser(){
        return "Hello User!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String greetAdmin(){
        return "Hello Admin!";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        }catch(AuthenticationException e){
            return ResponseEntity.badRequest().body("Invalid username or password!");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Person userDetails = (Person) authentication.getPrincipal();
        String jwt = jwtUtils.generateTokenFromUsername(userDetails);
        
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        LoginResponse loginResponse = new LoginResponse(jwt, userDetails.getPersonId(), userDetails.getUsername(), null, roles);
        return ResponseEntity.ok(loginResponse);

    }
}
