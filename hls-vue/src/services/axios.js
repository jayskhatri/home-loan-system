import axios from 'axios';

const instance = axios.create({
  baseURL: `https://loan-system-5r47.onrender.com/api/v1`,
  headers: {
    "Content-type": "application/json"
  }
});

export default instance;
