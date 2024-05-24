package com.tripod.homeloansystem.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripod.homeloansystem.models.LoanApplication;
import com.tripod.homeloansystem.repositories.LoanApplicationRepository;
import com.tripod.homeloansystem.repositories.PersonRepository;
import com.tripod.homeloansystem.services.LoanApplicationService;

@Service
public class LoanApplicationServiceImpl implements Serializable, LoanApplicationService{

    private static final long serialVersionUID = -1719768261698635617L;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public LoanApplication createLoanApplication(LoanApplication application) {
        return loanApplicationRepository.save(application);
    }

    @Override
    public Map.Entry<String, Boolean> updateLoanApplication(LoanApplication application) {
        LoanApplication applicationToUpdate = loanApplicationRepository.findById(application.getLoanApplicationId()).orElse(null);
        if(applicationToUpdate != null){
            applicationToUpdate.setPerson(application.getPerson());
            applicationToUpdate.setLoanType(application.getLoanType());
            applicationToUpdate.setLoanAmount(application.getLoanAmount());
            applicationToUpdate.setLoanDuration(application.getLoanDuration());
            applicationToUpdate.setLoanStatus(application.getLoanStatus());
            applicationToUpdate.setLoanInterestRate(application.getLoanInterestRate());
            applicationToUpdate.setLoanStartDate(application.getLoanStartDate());
            applicationToUpdate.setLoanEndDate(application.getLoanEndDate());
            loanApplicationRepository.save(applicationToUpdate);
            return Map.entry("Updated", true);
        }
        return Map.entry("Not Updated", false);
    }

    @Override
    public LoanApplication getLoanApplicationById(Long applicationId) {
        return loanApplicationRepository.findById(applicationId).orElse(null);
    }

    @Override
    public Entry<String, Boolean> deleteLoanApplicationById(Long applicationId) {
        LoanApplication applicationToDelete = loanApplicationRepository.findById(applicationId).orElse(null);
        if(applicationToDelete != null){
            loanApplicationRepository.delete(applicationToDelete);
            return Map.entry("Deleted", true);
        }
        return Map.entry("Not Deleted", false);
    }

    @Override
    public List<LoanApplication> getAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }

    public List<LoanApplication> getLoanApplicationsByPersonID(Long personID) {
        return loanApplicationRepository.findByPerson(personRepository.findById(personID).get());
    }
    
}
