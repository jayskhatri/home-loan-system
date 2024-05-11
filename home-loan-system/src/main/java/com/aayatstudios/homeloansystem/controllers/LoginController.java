package com.aayatstudios.homeloansystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aayatstudios.homeloansystem.services.impl.LoginServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password){
        System.out.println("Username: " + username + " Password: " + password);
        boolean authorized = loginService.validatePersonCredentials(username, password);
        if(authorized){
            return ResponseEntity.ok("User logged in");
        }else if(!authorized){
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        return ResponseEntity.notFound().build();
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
