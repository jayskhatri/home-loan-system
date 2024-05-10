package com.aayatstudios.homeloansystem.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aayatstudios.homeloansystem.models.Person;
import com.aayatstudios.homeloansystem.services.impl.PersonServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class PersonController {
    
    @Autowired
    private PersonServiceImpl personService;

    @GetMapping("/")
    public ResponseEntity<List<Person>> getCustomers(){
        List<Person> list = personService.getCustomers();
        if(list.size() > 0) return ResponseEntity.ok(list);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Person> registerUser(@RequestBody Person person){
        System.out.println("Person: " + person);
        Person newPerson = personService.createPerson(person);
        if(newPerson != null) return ResponseEntity.ok(newPerson);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Person> getUserById(@PathVariable Long userId){
        Person person = personService.getPersonById(userId);
        if(person != null) return ResponseEntity.ok(person);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<Map.Entry<String, Boolean>> updateUser(@PathVariable Long userId, @RequestBody Person person){
        System.out.println("Person: " + person);
        return ResponseEntity.ok(personService.updatePerson(person));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Map.Entry<String, Boolean>> deleteUser(@PathVariable Long userId){
        return ResponseEntity.ok(personService.deletePersonById(userId));
    }
    
}

/*
 * 
 { 
    "firstName": "John",
    "middleName": "",
    "lastName": "Doe",
    "username": "admin",
    "password": "admin",
    "phoneNumber": "1234567890",
    "address": "BLR",
    "dateOfBirth": "01/01/1999",
    "email": "john.doe@example.com",
    "isAdmin": true,
    "isFirstLogin": true
}

 * 
 */