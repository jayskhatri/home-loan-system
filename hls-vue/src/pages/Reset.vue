<template>
    <v-card
      class="mx-auto"
      max-width="500"
    >
      <v-card-title class="text-h6 font-weight-regular justify-space-between">
        <span>{{ currentTitle }}</span>
        <v-avatar
          color="primary"
          size="24"
          v-text="step"
        ></v-avatar>
      </v-card-title>

      <v-window v-model="step">
        <v-window-item :value="1">
          <v-card-text>
            <v-text-field v-model="username" label="Username"></v-text-field>
            <v-text-field v-model="phoneNumber" label="Phone number"></v-text-field>
            <v-text-field v-model="dob" label="DOB"></v-text-field>
          </v-card-text>
        </v-window-item>

        <v-window-item :value="2">
            <v-card-text>
                <v-text-field v-model="password" label="Password" type="password"></v-text-field>
                <v-text-field v-model="reenteredPassword" label="Re-Enter Password" type="password"></v-text-field>
                <span v-if="password !== reenteredPassword" class="text-caption text-error">
                    Passwords do not match
                </span>
            </v-card-text>
        </v-window-item>
      </v-window>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn v-if="step === 2" @click="updatePassword" :disabled="!(this.password && this.reenteredPassword && (this.password == this.reenteredPassword))"> Reset </v-btn>
        <v-spacer></v-spacer>
        <v-btn v-if="step === 1" color="primary" variant="flat" @click="verifyUserDetails">Verify</v-btn>
      </v-card-actions>
    </v-card>
  </template>
  <script>
    import LoginService from "@/services/login.service";
    export default {
        data: () => ({
            step: 1,
            username: '',
            phoneNumber: '',
            dob: '',
            password: '',
            reenteredPassword: ''
        }),

        computed: {
            currentTitle () {
                switch (this.step) {
                    case 1: return 'Enter details'
                    case 2: return 'Create a password'
                }
            },
        },
        methods: {
            clearVaraibles() {
                this.username = '';
                this.phoneNumber = '';
                this.dob = '';
            },
            updatePassword(){
                LoginService.updatePassword(this.username, this.password).then(response => {
                    alert('Password Updated Successfully! Redirecting to Login Page');
                    this.$router.push('/login');
                }).catch(error => {
                    this.step = 1;
                    this.clearVaraibles();
                    console.log('updatePassword: ' + error);
                });
            },
            verifyUserDetails() {
                LoginService.verifyUserDetails(this.username, this.phoneNumber, this.dob).then(response => {
                    if(response.data == true){
                        this.step++;
                    }else{
                        alert('Invalid User Details or User does not exist');
                        this.clearVaraibles();
                    }
                }).catch(error => {
                    console.log('verifyUserDetails: ' + error);
                });
            }
        }
    }
  </script>