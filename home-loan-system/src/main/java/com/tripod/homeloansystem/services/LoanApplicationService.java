package com.tripod.homeloansystem.services;

import java.util.List;
import java.util.Map;

import com.tripod.homeloansystem.models.LoanApplication;

public interface LoanApplicationService {
    public LoanApplication createLoanApplication(LoanApplication application);
    public Map.Entry<String,Boolean> updateLoanApplication(LoanApplication application);
    public LoanApplication getLoanApplicationById(Long applicationId);
    public Map.Entry<String, Boolean> deleteLoanApplicationById(Long applicationId);
    public Map.Entry<String, Boolean> approveLoanApplicationById(Long applicationId);
    public Map.Entry<String, Boolean> rejectLoanApplicationById(Long applicationId);
    public List<LoanApplication> getAllLoanApplications();
    
}
