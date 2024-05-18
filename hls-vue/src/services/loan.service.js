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
}

export default new LoanService();