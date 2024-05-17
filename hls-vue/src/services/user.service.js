import axios from './axios';

class UserService {
  
//   getPublicContent() {
//     return axios.get('/test/all');
//   }

  getCustomers() {
    return axios.get('/users/');
  }

  getCustomerById(id) {
    return axios.get(`/users/${id}`);
  }

  deleteCustomer(id) {
    return axios.delete(`/users/${id}/delete`);
  }

  addCustomer(customer) {
    return axios.post('/users/register', customer);
  }

  updateCustomer(id, customer) {
    return axios.put(`/users/${id}/update`, customer);
  }

  // addPerson(person){
  //   return axios.post('/users/register', person);
  // }

//   getModeratorBoard() {
//     return api.get('/test/mod');
//   }

//   getAdminBoard() {
//     return api.get('/test/admin');
//   }
}

export default new UserService();
