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
          <th class="text-left">
            Actions
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
          <td>
            <v-btn
              color="primary"
              @click="handleEditUser(customer)"
            >
              Edit
            </v-btn>
            <v-btn
              color="red"
              @click="handleDeleteUser(customer)"
            >
              Delete
            </v-btn>
          </td>
        </tr>
      </tbody>
    </v-table>
    <v-fab
            class="fab"
            icon="mdi-plus"
            size="small"
            color="primary"
            absolute
            @click="handleDialog"
      ></v-fab>


    <!--Dialog box to add user-->
  <v-dialog
      v-model="dialog"
      max-width="500"
    >
      <v-card>
        <v-card-text>
          <v-text-field
            v-model="firstName"
            label="First Name"
            :rules="firstNameRules"
          ></v-text-field>
          <v-text-field
            v-model="middleName"
            label="Middle Name"
          ></v-text-field>
          <v-text-field
            v-model="lastName"
            label="Last name"
            :rules="lastNameRules"
          ></v-text-field>
          <v-text-field
            v-model="phoneNumber"
            label="Phone Number"
            type="number"
            :rules="phoneNumberRules"
          ></v-text-field>
          <v-text-field
            v-model="username"
            label="Username"
          ></v-text-field>
          <v-text-field
            v-model="password"
            label="Password"
            :rules="passwordRules"
            type="password"
          ></v-text-field>
          <v-text-field
            v-model="address"
            label="Address"
          ></v-text-field>
          <v-text-field
            v-model="dateOfBirth"
            append-icon="mdi-calendar"
            hint="dd/mm/yyyy"
            label="Date of Birth"
          ></v-text-field>
          <v-text-field
            v-model="email"
            label="Email"
            type="email"          
            hint = "abc@gmail.com"
          ></v-text-field>
          <v-checkbox
            v-model="isAdmin"
            label="Is Admin"
          ></v-checkbox>

          <small class="text-grey">Pls make sure you handout password to customer</small>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="primary"
            variant="text"
            @click="handleAddUser"
          >
            {{ update ? 'Update' : 'Add' }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    
  </template>
  <script>
  import UserService from "@/services/user.service";
    export default {
      data () {
        return {
          customers: [],
          customer: {},
          update: true,
          dialog: false,
          firstName: '',
          middleName: '',
          lastName: '',
          username: '',
          password: '',
          phoneNumber: '',
          address: '',
          dateOfBirth: '',
          email: '',
          isAdmin: false,
          firstNameRules: [
            (v) => !!v || 'First Name is required'
          ],
          lastNameRules: [
            (v) => !!v || 'Last Name is required'
          ],
          phoneNumberRules: [
            (v) => (v && v.length == 10) || 'Phone Number is required & should be 10 Digit'
          ],
          passwordRules: [
            (v) => (v && v.length >=4) || 'Password must be at least 4 characters',
          ],
        }
      },
      computed: {
          currentUser() {
              return this.$store.state.auth.user;
          }
      },
      mounted() {
          if (!this.currentUser) {
            this.$router.push('/login');
          }
          this.getCustomers();
      },
      methods:{
        logOut() {
            this.$store.dispatch('auth/logout');
            this.$router.push('/login');
        },
        getCustomers(){
            UserService.getCustomers().then(response => {
              this.customers = response.data;
            }).catch(error => {
              console.log('getCustomers: ' + error);
              if(error.response.status === 401 || error.response.status === 403){
                this.logOut();
              }
            });
        },
        handleDialog(){
          this.dialog = !this.dialog;
          this.update = false;
          this.clearVariables();
        },

        clearVariables(){
          this.firstName = '';
          this.middleName = '';
          this.lastName = '';
          this.username = '';
          this.password = '';
          this.phoneNumber = '';
          this.address = '';
          this.dateOfBirth = '';
          this.email = '';
          this.isAdmin = false;
        },

        handleAddUser(){
          this.dialog = false;
          
          if(this.update){
            UserService.updateCustomer(this.customer.personId, {
              personId: this.customer.personId,
              firstName: this.firstName,
              middleName: this.middleName,
              lastName: this.lastName,
              username: this.username,
              password: this.password,
              phoneNumber: this.phoneNumber,
              address: this.address,
              dateOfBirth: this.dateOfBirth,
              email: this.email,
              isAdmin: this.isAdmin,
              isFirstLogin: this.customer.isFirstLogin
            }).then(response => {
              console.log(response.status);
              if (response.status === 200) {
                alert("User updated successfully.");
              } else {
                alert("Something went wrong. Please try again later.");
              }
            }).catch(error => {
              alert("Something went wrong. Please try again later.");
            });

            this.customers = this.customers.map(c => {
              if(c.personId === this.customer.personId){
                c.firstName = this.firstName;
                c.middleName = this.middleName;
                c.lastName = this.lastName;
                c.username = this.username;
                c.password = this.password;
                c.phoneNumber = this.phoneNumber;
                c.address = this.address;
                c.dateOfBirth = this.dateOfBirth;
                c.email = this.email;
                c.isAdmin = this.isAdmin;
              }
              return c;
            });
          }else{
            UserService.addCustomer({
              firstName: this.firstName,
              middleName: this.middleName,
              lastName: this.lastName,
              username: this.username,
              password: this.password,
              phoneNumber: this.phoneNumber,
              address: this.address,
              dateOfBirth: this.dateOfBirth,
              email: this.email,
              isAdmin: this.isAdmin,
              isFirstLogin: true
            }).then(response => {
              console.log(response.status);
              this.customers.push(response.data);
              alert("User added successfully.");
            }).catch(error => {
              alert("Something went wrong. Please try again later.");
            });
            this.clearVariables();
          }
        },

        handleEditUser(customer){
            this.customer = customer;
            this.firstName = customer.firstName;
            this.middleName = customer.middleName;
            this.lastName = customer.lastName;
            this.username = customer.username;
            this.password = customer.password;
            this.phoneNumber = customer.phoneNumber;
            this.address = customer.address;
            this.dateOfBirth = customer.dateOfBirth;
            this.email = customer.email;
            this.isAdmin = customer.isAdmin;
            this.dialog = true;
            this.update = true;
        },

        handleDeleteUser(customer){
          this.dialog = false;
          console.log(customer.personId);
          UserService.deleteCustomer(customer.personId).then(response => {
            console.log(response.status);
            if (response.status === 200) {
              alert("User deleted successfully.");
              this.customers = this.customers.filter(c => c.personId !== customer.personId);
            } else {
              alert("Something went wrong. Please try again later.");
            }
          }).catch(error => {
            alert("Something went wrong. Please try again later.");
          });
        }

      }
    }
  </script>