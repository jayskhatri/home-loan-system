<template>
  <v-sheet class="mx-auto" width="300">
    <v-form @submit.prevent="handleSubmit">
      <v-text-field
        v-model="username"
        :rules="usernameRules"
        label="Username"
      ></v-text-field>
      <v-text-field
        v-model="password"
        :rules="passwordRules"
        label="Password"
        type="password"
      ></v-text-field>
      <v-btn class="mt-2" type="submit" block>Submit</v-btn>
    </v-form>
  </v-sheet>
</template>
<script>
import axios from 'axios';
  export default {
    data() {
      return {
      username: '',
      password: '',
      usernameRules: [
        (v) => !!v || 'Username is required'
      ],
      passwordRules: [
        (v) => (v && v.length >=4) || 'Password must be at least 4 characters',
      ],
      };
    },
    methods: {
      handleSubmit() {
        console.log('First name:', this.username);
        console.log('Last name:', this.password);
        console.log('http://localhost:8080/api/v1/login?username=' + this.username + '&password=' + this.password);
        
          axios.post('http://localhost:8080/api/v1/login?username=' + this.username + '&password=' + this.password, {
            username: this.username,
            password: this.password
          })
          .then(response => {
            // Handle the response from the API
            console.log(response.status);

            if (response.status === 200) {
              this.$router.push('/');
            } else {
              alert("Something went wrong. Please try again later.");
            }
          })
          .catch(error => {
            if(error.response.status === 401) {
              alert("Invalid username or password. Please try again.");
            } else {
              alert("Something went wrong. Please try again later.");
            }
          });
        },
    },
  };
</script>