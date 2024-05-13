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

import com.tripod.homeloansystem.jwt.JwtUtils;
import com.tripod.homeloansystem.jwt.LoginRequest;
import com.tripod.homeloansystem.jwt.LoginResponse;
import com.tripod.homeloansystem.models.Person;
import com.tripod.homeloansystem.services.impl.PersonServiceImpl;


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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateTokenFromUsername(userDetails);
        
        
        Person person = personService.getPersonByUsername(loginRequest.getUsername());
        LoginResponse loginResponse;
        
        if(person!=null){
            if (person.getIsAdmin()) {
                loginResponse = new LoginResponse(jwt, userDetails.getUsername(), List.of("ADMIN", "USER"));
            } else {
                loginResponse = new LoginResponse(jwt, userDetails.getUsername(), List.of("USER"));
            }
            return ResponseEntity.ok(loginResponse);
        }else{
            return ResponseEntity.notFound().build();
        }
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
