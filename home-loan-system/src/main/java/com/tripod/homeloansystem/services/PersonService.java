package com.tripod.homeloansystem.services;

import java.util.List;
import java.util.Map;

import com.tripod.homeloansystem.models.Person;

public interface PersonService {
    
    public Person createPerson(Person person);
    public Map.Entry<String,Boolean> updatePerson(Person person);
    public Person getPersonById(Long personId);
    public Person getPersonByUsername(String username);
    public Map.Entry<String, Boolean> deletePersonById(Long personId);

    //get customers 
    public List<Person> getCustomers();
} 