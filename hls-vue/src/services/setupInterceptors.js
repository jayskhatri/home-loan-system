import axios from "./axios";
import TokenService from "./token.service";

const setup = (store) => {
  axios.interceptors.request.use(
    (config) => {
      const token = TokenService.getLocalAccessToken();
      if (token) {
        config.headers["Authorization"] = 'Bearer ' + token;  // for Spring Boot back-end
        // config.headers["x-access-token"] = token; // for Node.js Express back-end
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  axios.interceptors.response.use(
    (res) => {
      return res;
    },
    async (err) => {
      const originalConfig = err.config;
      if (originalConfig.url !== "/login" && err.response) {
        // Access Token was expired
        if (err.response.status === 401 && !originalConfig._retry) {
          originalConfig._retry = true;

          try {
            const rs = await axios.post("refreshtoken", {
              refreshToken: TokenService.getLocalRefreshToken(),
            });
            
            store.dispatch('auth/refreshToken', rs.data.jwtToken); //?????
            TokenService.updateLocalAccessTokenAndRefreshToken(rs.data.jwtToken, rs.data.refreshToken);
            
            return axios(originalConfig);
          } catch (_error) {
            store.dispatch('auth/logout');
            return Promise.reject(_error);
          }
        }
      }
      // store.dispatch('auth/logout');
      return Promise.reject(err);
    }
  );
};

export default setup;