import axios from './axios';

class LoanService {
    getLoanApplications(){
        return axios.get('/loan/all');
    }

    getLoanApplicationsByPersonID(id){
        return axios.get(`/loan/${id}/all`);
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

    deleteLoanApplicationByID(id){
        return axios.delete(`/loan/${id}`);
    }
}

export default new LoanService();