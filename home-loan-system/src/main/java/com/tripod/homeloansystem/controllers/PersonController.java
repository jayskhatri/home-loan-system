package com.tripod.homeloansystem.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripod.homeloansystem.models.Person;
import com.tripod.homeloansystem.models.dto.PersonDTO;
import com.tripod.homeloansystem.repositories.RefreshTokenRepository;
import com.tripod.homeloansystem.services.impl.PersonServiceImpl;

import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class PersonController {
    
    @Autowired
    private PersonServiceImpl personService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<PersonDTO>> getCustomers(){
        List<PersonDTO> listDTO = personService.getCustomers().stream().map(person -> personToDTO(person)).toList();
        if(listDTO.size() > 0) return ResponseEntity.ok(listDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<PersonDTO> registerUser(@RequestBody Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Person newPerson = personService.createPerson(person);
        if(newPerson != null) return ResponseEntity.ok(personToDTO(newPerson));
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<PersonDTO> getUserById(@PathVariable Long userId){
        Person person = personService.getPersonById(userId);
        if(person != null) return ResponseEntity.ok(personToDTO(person));
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/update")
    public ResponseEntity<Map.Entry<String, Boolean>> updateUser(@PathVariable Long userId, @RequestBody Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return ResponseEntity.ok(personService.updatePerson(person));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}/delete")
    @Transactional
    public ResponseEntity<Map.Entry<String, Boolean>> deleteUser(@PathVariable Long userId){
        Person person = personService.getPersonById(userId);
        refreshTokenRepository.deleteByUser(person);
        return ResponseEntity.ok(personService.deletePersonById(userId));
    }

    @PostMapping("/init")
    public ResponseEntity<PersonDTO> init(@RequestBody Person person){
        System.out.println("Person: " + person);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Person newPerson = personService.createPerson(person);
        if(newPerson != null) return ResponseEntity.ok(personToDTO(newPerson));
        return ResponseEntity.noContent().build();
    }

    public static PersonDTO personToDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();

        personDTO.setPersonId(person.getPersonId());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setMiddleName(person.getMiddleName());
        personDTO.setLastName(person.getLastName());
        personDTO.setUsername(person.getUsername());
        personDTO.setPassword(person.getPassword());
        personDTO.setEmail(person.getEmail());
        personDTO.setPhoneNumber(person.getPhoneNumber());
        personDTO.setAddress(person.getAddress());
        personDTO.setDateOfBirth(person.getDateOfBirth());
        personDTO.setIsAdmin(person.getIsAdmin());
        List<Long> loanApplications = person.getLoanApplications().stream().map(loan -> loan.getLoanApplicationId()).toList();
        personDTO.setLoanApplications(loanApplications);
        return personDTO;
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