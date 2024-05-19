import axios from './axios';

class LoginService {

    verifyUserDetails(username, phoneNumber, dob){
        return axios.get('/verify', {
            params: {
                username: username,
                phoneNumber: phoneNumber,
                dob: dob
            }
        });
    }

    updatePassword(username, password){
        return axios.patch('/update-password', null, {
            params: {
                username: username,
                password: password
            }
        });
    }
}
export default new LoginService();