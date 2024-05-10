package com.aayatstudios.homeloansystem.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aayatstudios.homeloansystem.exceptions.ResourceNotFoundException;
import com.aayatstudios.homeloansystem.models.Person;
import com.aayatstudios.homeloansystem.repositories.PersonRepository;
import com.aayatstudios.homeloansystem.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person createPerson(Person person) {
        personRepository.save(person);
        return person;
    }

    @Override
    public Map.Entry<String, Boolean> updatePerson(Person person) {
        Person personFromRepo = personRepository.findById(person.getPersonId()).orElseThrow(() -> new RuntimeException("Person not found"));
        if(personFromRepo != null){
            personFromRepo.setFirstName(person.getFirstName());
            personFromRepo.setMiddleName(person.getMiddleName());
            personFromRepo.setLastName(person.getLastName());
            personFromRepo.setUsername(person.getUsername());
            personFromRepo.setPassword(person.getPassword());
            personFromRepo.setEmail(person.getEmail());
            personFromRepo.setPhoneNumber(person.getPhoneNumber());
            personFromRepo.setAddress(person.getAddress());
            personFromRepo.setDateOfBirth(person.getDateOfBirth());
            personFromRepo.setIsAdmin(person.getIsAdmin());
            personFromRepo.setIsFirstLogin(person.getIsFirstLogin());
            personRepository.save(personFromRepo);
            return Map.entry("Updated", Boolean.TRUE);
        }
        return Map.entry("Updated", Boolean.FALSE);
    }

    @Override
    public Person getPersonById(Long personId) {
        return personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    public Map.Entry<String, Boolean> deletePersonById(Long personId) {
        Person person = personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        if(person != null){
            personRepository.delete(person);
            return Map.entry("Deleted", Boolean.TRUE);
        }
        return Map.entry("Deleted", Boolean.FALSE);
    }

    @Override
    public List<Person> getCustomers() {
        return personRepository.findPersonByIsAdmin(false);
    }


}
