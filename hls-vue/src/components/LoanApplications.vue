<template>
    <v-table>
      <thead>
        <tr>
          <th class="text-left">
            ID
          </th>
          <th class="text-left">
            Name
          </th>
          <th class="text-left">
            Username
          </th>
          <th class="text-left">
            Phone Number
          </th>
          <th class="text-left">
            Email Address
          </th>
          <th class="text-left">
            Address
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="customer in customers"
          :key="customer.personId"
        >
          <td>{{ customer.personId }}</td>
          <td>{{ customer.firstName }} {{ customer.lastName }}</td>
          <td>{{ customer.username }}</td>
          <td>{{ customer.phoneNumber }}</td>
          <td>{{ customer.email }}</td>
          <td>{{ customer.address }}</td>
        </tr>
      </tbody>
    </v-table>
  </template>
  <script>
  import axios from 'axios';
    export default {
      data () {
        return {
          customers: [],
        }
      },
      mounted() {
        axios.get('http://localhost:8080/api/v1/users/')
          .then(response => {
            console.log(response.data);
            this.customers = response.data;
          })
          .catch(error => {
            
          });
      },
    }
  </script>