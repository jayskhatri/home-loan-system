package com.tripod.homeloansystem.models.dto;

import com.tripod.homeloansystem.models.LoanType;

import lombok.Data;

@Data
public class LoanApplicationDTO {
    private Long loanApplicationId;
    private Long personId;
    private LoanType loanType;
    private String loanAmount;
    private String loanDuration;
    private String loanStatus;
    private String loanInterestRate;
    private String loanStartDate;
    private String loanEndDate;

}
