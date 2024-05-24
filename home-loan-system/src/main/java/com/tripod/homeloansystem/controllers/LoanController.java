package com.tripod.homeloansystem.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripod.homeloansystem.models.LoanApplication;
import com.tripod.homeloansystem.models.LoanStatus;
import com.tripod.homeloansystem.models.Person;
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
    @PatchMapping("/{applicationID}/update")
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/all")
    public ResponseEntity<List<LoanApplicationDTO>> getLoanApplicationsByPersonID(@PathVariable Long id) {
        System.out.println("Inside getLoanApplicationsByPersonID");
        return ResponseEntity.ok(loanApplicationService.getLoanApplicationsByPersonID(id).stream().map(application -> loanApplicationToDTO(application)).toList());
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{applicationID}")
    public ResponseEntity<Map.Entry<String, Boolean>> deleteLoanApplicationByID(@PathVariable Long applicationID) {
        return ResponseEntity.ok(loanApplicationService.deleteLoanApplicationById(applicationID));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{applicationID}/save")
    public ResponseEntity<?> saveLoanApplication(@PathVariable Long applicationID) {
        LoanApplication application = loanApplicationService.getLoanApplicationById(applicationID);
        if(application != null && application.getIsSubmitted().equals(Boolean.FALSE)){
            application.setIsSubmitted(Boolean.TRUE);
            loanApplicationService.updateLoanApplication(application);
            return ResponseEntity.ok("SAVED");
        } else if(application != null){
            return ResponseEntity.badRequest().body("Loan is already saved");
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{applicationID}/approve")
    public ResponseEntity<?> approveLoanApplication(@PathVariable Long applicationID) {
        LoanApplication application = loanApplicationService.getLoanApplicationById(applicationID);
        if(application != null && application.getLoanStatus().equals(LoanStatus.PENDING)){
            application.setLoanStatus(LoanStatus.APPROVED);
            loanApplicationService.updateLoanApplication(application);
            return ResponseEntity.ok("APPROVED");
        } else if(application != null){
            return ResponseEntity.badRequest().body("Loan is already approved or it was rejected");
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{applicationID}/reject")
    public ResponseEntity<?> rejectLoanApplication(@PathVariable Long applicationID) {
        LoanApplication application = loanApplicationService.getLoanApplicationById(applicationID);
        if(application != null && application.getLoanStatus().equals(LoanStatus.PENDING)){
            application.setLoanStatus(LoanStatus.REJECTED);
            loanApplicationService.updateLoanApplication(application);
            return ResponseEntity.ok("REJECTED");
        } else if(application != null){
            return ResponseEntity.badRequest().body("Loan is already approved or it was rejected");
        }
        return ResponseEntity.notFound().build();
    }

    private LoanApplication loanDTOtoApplication(LoanApplicationDTO applicationDTO) {
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
        application.setIsSubmitted(applicationDTO.getIsSubmitted());
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
        applicationDTO.setIsSubmitted(application.getIsSubmitted());
        return applicationDTO;
    }
    
}
