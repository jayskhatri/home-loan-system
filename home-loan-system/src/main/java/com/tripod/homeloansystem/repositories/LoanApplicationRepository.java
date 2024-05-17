package com.tripod.homeloansystem.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripod.homeloansystem.models.LoanApplication;
import com.tripod.homeloansystem.models.LoanStatus;
import com.tripod.homeloansystem.models.Person;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long>{
    Optional<List<LoanApplication>> findByLoanStatus(LoanStatus loanStatus);
    Optional<List<LoanApplication>> findByPerson(Person person);
}