package com.aayatstudios.homeloansystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aayatstudios.homeloansystem.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    public Person findByUsername(String username);
    public Person findByEmail(String email);
    public List<Person> findPersonByIsAdmin(boolean isAdmin);
}
