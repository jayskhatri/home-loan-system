import axios from "./axios";
import TokenService from "./token.service";

class AuthService {
  login({ username, password }) {
    return axios
      .post("/login", {
        username,
        password
      })
      .then((response) => {
        console.log(response.data);
        if (response.data.id) {
          TokenService.setUser(response.data);
        }
        return response.data;
      });
  }

  logout() {
    TokenService.removeUser();
  }

//   register({ username, email, password }) {
//     return axios.post("/auth/signup", {
//       username,
//       email,
//       password
//     });
//   }
}

export default new AuthService();


/**
LOGIN RESPONSE 
{
    "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNTc5MDIzMywiZXhwIjoxNzE1NzkwMjkzfQ.lm8kmfLsYLEYUfuMEQdOymLz_C6DHUnbdmcey9d7Was",
    "id": 502,
    "username": "admin",
    "type": "Bearer",
    "refreshToken": {
        "id": 44,
        "token": "d0e736e3-eff9-4db3-abb8-90325e4cadc4",
        "expiryDate": "2024-05-15T16:43:53.993870Z"
    },
    "roles": [
        "ADMIN"
    ]
}
  
 */