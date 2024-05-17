class TokenService {
    getLocalRefreshToken() {
      const user = JSON.parse(localStorage.getItem("user"));
      return user?.refreshToken.token;
    }
  
    getLocalAccessToken() {
      const user = JSON.parse(localStorage.getItem("user"));
      return user?.jwtToken;
    }
  
    updateLocalAccessTokenAndRefreshToken(accessToken, refreshToken) {
      let user = JSON.parse(localStorage.getItem("user"));
      user.jwtToken = accessToken;
      user.refreshToken.token = refreshToken
      localStorage.setItem("user", JSON.stringify(user));
    }
  
    getUser() {
      return JSON.parse(localStorage.getItem("user"));
    }
  
    setUser(user) {
      console.log(JSON.stringify(user));
      localStorage.setItem("user", JSON.stringify(user));
    }
  
    removeUser() {
      localStorage.removeItem("user");
    }
  }
  
  export default new TokenService();
  
  /**
   * 
   
   */