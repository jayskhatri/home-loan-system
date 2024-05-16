package com.tripod.homeloansystem.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripod.homeloansystem.exceptions.ResourceNotFoundException;
import com.tripod.homeloansystem.models.Person;
import com.tripod.homeloansystem.repositories.PersonRepository;
import com.tripod.homeloansystem.services.PersonService;

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
        Person personFromRepo = personRepository.findById(person.getPersonId()).orElse(null);
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
        return personRepository.findById(personId).orElse(null);
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Map.Entry<String, Boolean> deletePersonById(Long personId) {
        Person person = personRepository.findById(personId).orElse(null);
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
