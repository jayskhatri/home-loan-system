package com.tripod.homeloansystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripod.homeloansystem.models.Person;
import com.tripod.homeloansystem.repositories.PersonRepository;
import com.tripod.homeloansystem.services.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Boolean validatePersonCredentials(String username, String password) {
        Person person = personRepository.findByUsername(username);
        if(person != null){
            if(person.getPassword().equals(password)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
    
}
