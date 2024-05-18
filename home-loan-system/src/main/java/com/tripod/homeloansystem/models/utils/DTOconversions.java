package com.tripod.homeloansystem.models.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tripod.homeloansystem.models.LoanApplication;
import com.tripod.homeloansystem.models.Person;
import com.tripod.homeloansystem.models.dto.LoanApplicationDTO;
import com.tripod.homeloansystem.models.dto.PersonDTO;
import com.tripod.homeloansystem.services.impl.LoanApplicationServiceImpl;
import com.tripod.homeloansystem.services.impl.PersonServiceImpl;

public class DTOconversions {

    @Autowired
    private static PersonServiceImpl personService;

    @Autowired
    private static LoanApplicationServiceImpl loanApplicationService;

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

    private static Person personDTOToPerson(PersonDTO personDTO){
        Person person = new Person();
        person.setPersonId(personDTO.getPersonId());
        person.setFirstName(personDTO.getFirstName());
        person.setMiddleName(personDTO.getMiddleName());
        person.setLastName(personDTO.getLastName());
        person.setUsername(personDTO.getUsername());
        person.setPassword(personDTO.getPassword());
        person.setEmail(personDTO.getEmail());
        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setAddress(personDTO.getAddress());
        person.setDateOfBirth(personDTO.getDateOfBirth());
        person.setIsAdmin(personDTO.getIsAdmin());
        List<LoanApplication> loanApplications = personDTO.getLoanApplications().stream().map(loanId -> loanApplicationService.getLoanApplicationById(loanId)).toList();
        person.setLoanApplications(loanApplications);
        return person;
    }

    public static LoanApplication loanDTOtoApplication(LoanApplicationDTO applicationDTO) {
        LoanApplication application = new LoanApplication();
        application.setLoanApplicationId(applicationDTO.getLoanApplicationId());
        application.setLoanType(applicationDTO.getLoanType());
        application.setLoanAmount(applicationDTO.getLoanAmount());
        application.setLoanDuration(applicationDTO.getLoanDuration());
        application.setLoanStartDate(applicationDTO.getLoanStartDate());
        application.setLoanEndDate(applicationDTO.getLoanEndDate());
        application.setLoanInterestRate(applicationDTO.getLoanInterestRate());
        application.setLoanStatus(applicationDTO.getLoanStatus());
        Person person = personService.getPersonById(applicationDTO.getPersonId());
        application.setPerson(person);
        return application;
    }

    public static LoanApplicationDTO loanApplicationToDTO(LoanApplication application) {
        LoanApplicationDTO applicationDTO = new LoanApplicationDTO();
        applicationDTO.setLoanApplicationId(application.getLoanApplicationId());
        applicationDTO.setPersonId(application.getPerson().getPersonId());
        applicationDTO.setLoanType(application.getLoanType());
        applicationDTO.setLoanAmount(application.getLoanAmount());
        applicationDTO.setLoanDuration(application.getLoanDuration());
        applicationDTO.setLoanStartDate(application.getLoanStartDate());
        applicationDTO.setLoanEndDate(application.getLoanEndDate());
        applicationDTO.setLoanInterestRate(application.getLoanInterestRate());
        applicationDTO.setLoanStatus(application.getLoanStatus());
        return applicationDTO;
    }
}
