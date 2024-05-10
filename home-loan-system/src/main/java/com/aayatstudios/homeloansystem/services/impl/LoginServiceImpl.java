package com.aayatstudios.homeloansystem.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aayatstudios.homeloansystem.models.Person;
import com.aayatstudios.homeloansystem.repositories.PersonRepository;
import com.aayatstudios.homeloansystem.services.LoginService;

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
