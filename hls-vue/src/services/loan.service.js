import axios from './axios';

class LoanService {
    getLoanApplications(){
        return axios.get('/loan/all');
    }

    approveLoanApplication(id){
        return axios.patch(`/loan/${id}/approve`);
    }

    rejectLoanApplication(id){
        return axios.patch(`/loan/${id}/reject`);
    }

    applyLoanApplication(loanDetails){
        return axios.post('/loan/create', loanDetails);
    }

    saveSubmittedLoanApplication(id){
        return axios.patch(`/loan/${id}/save`);
    }
}

export default new LoanService();