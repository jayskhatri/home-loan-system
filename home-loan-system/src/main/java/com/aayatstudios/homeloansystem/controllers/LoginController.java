package com.aayatstudios.homeloansystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aayatstudios.homeloansystem.services.impl.LoginServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;
    
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password){
        System.out.println("Username: " + username + " Password: " + password);
        if(loginService.validatePersonCredentials(username, password)){
            return "User logged in";
        }
        return "Invalid credentials";
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
