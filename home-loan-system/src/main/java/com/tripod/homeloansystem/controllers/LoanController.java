package com.tripod.homeloansystem.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripod.homeloansystem.models.LoanApplication;
import com.tripod.homeloansystem.models.dto.LoanApplicationDTO;
import com.tripod.homeloansystem.services.impl.LoanApplicationServiceImpl;
import com.tripod.homeloansystem.services.impl.PersonServiceImpl;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/loan")
public class LoanController {

    @Autowired
    private LoanApplicationServiceImpl loanApplicationService;

    @Autowired
    private PersonServiceImpl personService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<LoanApplicationDTO> createLoanApplication(@RequestBody LoanApplicationDTO application) {
        return ResponseEntity.ok(loanApplicationToDTO(loanApplicationService.createLoanApplication(loanDTOtoApplication(application))));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{applicationID}/update")
    public ResponseEntity<Map.Entry<String, Boolean>> updateLoanApplication(@PathVariable Long applicationID, @RequestBody LoanApplicationDTO application) {
        return ResponseEntity.ok(loanApplicationService.updateLoanApplication(loanDTOtoApplication(application)));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{applicationID}")
    public ResponseEntity<LoanApplicationDTO> getLoanApplicationById(@PathVariable Long applicationID) {
        return ResponseEntity.ok(loanApplicationToDTO(loanApplicationService.getLoanApplicationById(applicationID)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<LoanApplicationDTO>> getAllLoanApplications() {
        return ResponseEntity.ok(loanApplicationService.getAllLoanApplications().stream().map(application -> loanApplicationToDTO(application)).toList());
    }

    private LoanApplication loanDTOtoApplication(LoanApplicationDTO applicationDTO) {
        LoanApplication application = new LoanApplication();
        application.setLoanApplicationId(applicationDTO.getLoanApplicationId());
        application.setPerson(personService.getPersonById(applicationDTO.getPersonId()));
        application.setLoanType(applicationDTO.getLoanType());
        application.setLoanAmount(applicationDTO.getLoanAmount());
        application.setLoanDuration(applicationDTO.getLoanDuration());
        application.setLoanStartDate(applicationDTO.getLoanStartDate());
        application.setLoanEndDate(applicationDTO.getLoanEndDate());
        application.setLoanInterestRate(applicationDTO.getLoanInterestRate());
        application.setLoanStatus(applicationDTO.getLoanStatus());
        return application;
    }

    private LoanApplicationDTO loanApplicationToDTO(LoanApplication application) {
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
