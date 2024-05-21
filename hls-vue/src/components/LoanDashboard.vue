<template>
  <v-card flat>
    <v-card-title class="d-flex align-center pe-2">
      <v-icon icon="mdi-video-input-component"></v-icon> &nbsp;
      Find a Loan Application

      <v-spacer></v-spacer>

      <v-text-field
        v-model="search"
        density="compact"
        label="Search"
        prepend-inner-icon="mdi-magnify"
        variant="solo-filled"
        flat
        hide-details
        single-line
      ></v-text-field>
    </v-card-title>

    <v-divider></v-divider>
    <v-data-table 
      v-model:search="search" 
      :headers="headers" 
      :items="loanApplications" 
      :loading="loadingTable"
      >
      <template v-slot:header.loanApplicationId>
        <div>ID</div>
      </template>

      <template v-slot:header.personId>
        <div>Person ID</div>
      </template>

      <template v-slot:header.loanType>
        <div>Type</div>
      </template>

      <template v-slot:header.loanStatus>
        <div>Loan Status</div>
      </template>

      <template v-slot:header.loanAmount>
        <div>Amount</div>
      </template>

      <template v-slot:header.loanDuration>
        <div>Duration</div>
      </template>

      <template v-slot:header.loanInterestRate>
        <div>Interest</div>
      </template>

      <template v-slot:header.loanStartDate>
        <div>Start Date</div>
      </template>

      <template v-slot:header.loanEndDate>
        <div>End Date</div>
      </template>

      <template v-slot:header.isSubmitted>
        <div>Is Submitted</div>
      </template>

      <template v-slot:header.actions="{ header }">
        <div class="text-center">Take Action</div>
      </template>

      <template v-slot:item.actions="{ item }">
        <div class="d-flex justify-center">
          <v-btn :disabled="!item.isSubmitted" color="green" @click="approveLoan(item.loanApplicationId)">APPROVE</v-btn>
          <v-btn :disabled="!item.isSubmitted" color="red" @click="rejectLoan(item.loanApplicationId)">REJECT</v-btn>
        </div>
      </template>

      <template v-slot:item.isSubmitted="{ item }">
        <v-chip
          :color="item.isSubmitted === true ? 'green' : 'red'"
          :text="item.isSubmitted === true ? 'YES' : 'NO'"
          class="text-uppercase"
          size="small"
          label
        ></v-chip>
      </template>

      <template v-slot:item.loanType="{ item }">
        <div @click="handleClick(item)">
          {{ item.loanType }}
        </div>
      </template>

      <template v-slot:item.loanStatus="{ item }">
        <div>
          <v-chip
            :color="item.loanStatus === 'APPROVED' ? 'green' : item.loanStatus === 'REJECTED' ? 'red' : 'orange'"
            :text="item.loanStatus"
            class="text-uppercase"
            size="small"
            label
          ></v-chip>
        </div>
      </template>
    </v-data-table>

    <v-navigation-drawer
      v-model="drawer"
      temporary
      width="1000"
      location="bottom"
    >
      <v-list-item
        prepend-avatar="https://randomuser.me/api/portraits/men/78.jpg"
        append-icon="mdi-close"
      >Loan Application submitted by: {{this.applicant.firstName}} {{this.applicant.lastName}}
      </v-list-item>      

      <v-divider></v-divider>

      <v-row>
        <v-col cols="12" class="text-center">
          <h2>Person Details</h2>
        </v-col>
      </v-row>
      <v-divider></v-divider>
      <v-row>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Username</v-card-title>
              <v-card-subtitle>{{this.applicant.username}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Birth Date</v-card-title>
              <v-card-subtitle>{{this.applicant.dateOfBirth}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Phone Number</v-card-title>
              <v-card-subtitle>{{this.applicant.phoneNumber}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Email</v-card-title>
              <v-card-subtitle>{{this.applicant.email}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
      </v-row>

      <v-divider></v-divider>
      <v-row>
        <v-col cols="12" class="text-center">
          <h2>Application Details</h2>
        </v-col>
      </v-row>
      <v-divider></v-divider>
      
      <v-row>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Loan Amount</v-card-title>
              <v-card-subtitle>{{this.loanApplication.loanAmount}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Loan Interest</v-card-title>
              <v-card-subtitle>{{this.loanApplication.loanInterestRate==='' ? '-' : this.loanApplication.loanInterestRate}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Duration of Loan</v-card-title>
              <v-card-subtitle>{{this.loanApplication.loanDuration}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Start date</v-card-title>
              <v-card-subtitle>{{this.loanApplication.loanStartDate}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Loan Submitted?</v-card-title>
              <v-card-subtitle>
                <v-chip
                :color="this.loanApplication.isSubmitted === true ? 'green' : 'red'"
                :text="this.loanApplication.isSubmitted === true ? 'YES' : 'NO'"
                class="text-uppercase"
                size="small"
                label
              ></v-chip>
              </v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Loan Type</v-card-title>
              <v-card-subtitle>{{this.loanApplication.loanType==='HOME_LOAN' ? 'HOME' : this.loanApplication.loanType==='EDUCATION_LOAN' ? 'EDUCATION' : this.loanApplication.loanType==='PERSONAL_LOAN' ? 'PERSONAL' : 'CAR'}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>Loan Status</v-card-title>
              <v-card-subtitle>
                <v-chip
                  :color="this.loanApplication.loanStatus === 'APPROVED' ? 'green' : this.loanApplication.loanStatus === 'REJECTED' ? 'red' : 'orange'"
                  :text="this.loanApplication.loanStatus"
                  class="text-uppercase"
                  size="small"
                  label
                ></v-chip>
              </v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
        <v-col cols="12" md="3">
          <v-card
           link>
            <v-card-item>
              <v-card-title>End date</v-card-title>
              <v-card-subtitle>{{this.loanApplication.loanEndDate}}</v-card-subtitle>
            </v-card-item>
          </v-card>
        </v-col>
      </v-row>

      <v-expansion-panels class="my-4" variant="popout">
        <v-expansion-panel
          text="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
          title="EMI CALCULATION"
        ></v-expansion-panel>
     </v-expansion-panels>
    </v-navigation-drawer>
  </v-card>
</template>
<script>
import LoanService from '@/services/loan.service'
import userService from '@/services/user.service';
  export default {
      data () {
          return {
            drawer: null,
            loadingTable: false,
            headers: [
              { text: 'ID', value: 'loanApplicationId' },
              { text: 'Person ID', value: 'personId' },
              { text: 'Loan Type', value: 'loanType' },
              { text: 'Loan Status', value: 'loanStatus' },
              { text: 'Loan Amount', value: 'loanAmount' },
              { text: 'Loan Duration', value: 'loanDuration' },
              { text: 'Loan Interest Rate', value: 'loanInterestRate' },
              { text: 'Loan Start Date', value: 'loanStartDate' },
              { text: 'Loan End Date', value: 'loanEndDate'},
              { text: 'Submitted?', value: 'isSubmitted'},
              { text: 'Actions', value: 'actions' },
            ],
              search: '',
              loanApplications: [],
              loanApplication: {},
              applicant: {},
              dialog: false,
              update: true,
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
        this.getLoanApplications();
      },
      methods: {
          getLoanApplications(){
              this.loadingTable = true;
              setTimeout(() => {
                LoanService.getLoanApplications().then(response =>{
                    this.loanApplications = response.data;
                }).catch(error => {
                    console.log('getLoans: ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                });

                this.loadingTable = false;
              }, 1000);
          },

          approveLoan(id){
              if (confirm("Do you really want to approve the loan?")) {
                LoanService.approveLoanApplication(id).then(response =>{
                  this.getLoanApplications();
                }).catch(error => {
                  console.log('approveLoan: ' + error);
                  if(error.response.status === 400){
                      alert('Loan cannot be approved as it was rejected already.');
                  }
                  if(error.response.status === 401 || error.response.status === 403){
                    this.logOut();
                  }
                })
              }
          },

          rejectLoan(id){
            if (confirm("Do you really want to reject the loan?")) {
              LoanService.rejectLoanApplication(id).then(response =>{
                  this.getLoanApplications();
              }).catch(error => {
                  console.log('rejectLoan: ' + error);
                  if(error.response.status === 400){
                      alert('Loan cannot be rejected as it was approved already.');
                  }
                  if(error.response.status === 401 || error.response.status === 403){
                      this.logOut();
                  }
              });
            }
          },
          async handleClick(item){
              this.drawer = !this.drawer;
              this.loanApplication = this.loanApplications.find(loan => loan.loanApplicationId === item.loanApplicationId);
              try{
                const response = await userService.getCustomerById(this.loanApplication.personId);
                this.applicant = response.data;
              }catch(error){
                console.log('LoanDashBoard::getCustomerById: ' + error);
              }
              console.log(this.loanApplication);
              console.log('Hello, you clicked? ' + item.loanApplicationId + ' --> ' + this.applicant.personId);
          },
          logOut(){
              this.$store.dispatch('auth/logout');
              this.$router.push('/login');
          }
      }
  }

</script>
