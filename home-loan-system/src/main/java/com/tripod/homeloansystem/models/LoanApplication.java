package com.tripod.homeloansystem.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "loan_applications")
public class LoanApplication implements Serializable{
    private static final long serialVersionUID = 3705895334919324848L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_application_id")
    private Long loanApplicationId;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "loan_type")
    private LoanType loanType;

    @Column(name = "loan_amount")
    private String loanAmount;

    @Column(name = "loan_duration")
    private String loanDuration;

    @Column(name = "loan_status")
    private LoanStatus loanStatus;

    @Column(name = "loan_interest_rate")
    private String loanInterestRate;

    @Column(name = "loan_start_date")
    private String loanStartDate;

    @Column(name = "loan_end_date")
    private String loanEndDate;


    @Override
    public String toString(){
        return "LoanApplication [loanApplicationId=" + loanApplicationId + 
        ", person=" + person.getPersonId() + ", loanType=" + loanType + 
        ", loanAmount=" + loanAmount + ", loanDuration=" + loanDuration + 
        ", loanStatus=" + loanStatus + ", loanInterestRate=" + loanInterestRate + 
        ", loanStartDate=" + loanStartDate + ", loanEndDate=" + loanEndDate + "]";
    }
}

/*

{
    "loanApplicationId": 1,
    "person_id": 1, // person_id is a foreign key, update this based on user
    "loanType": "Home",
    "loanAmount": "1000000",
    "loanDuration": "10",
    "loanStatus": "Pending",
    "loanInterestRate": "10",
    "loanStartDate": "2021-10-01",
    "loanEndDate": "2031-10-01",
}
 */