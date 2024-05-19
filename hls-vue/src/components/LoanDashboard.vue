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
    <v-data-table v-model:search="search" :headers="headers" :items="loanApplications">
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

      <template v-slot:header.actions="{ header }">
        <div class="text-center">Take Action</div>
      </template>

      <template v-slot:item.actions="{ item }">
        <div class="d-flex justify-center">
          <v-btn color="green" @click="approveLoan(item.loanApplicationId)">APPROVE</v-btn>
          <v-btn color="red" @click="rejectLoan(item.loanApplicationId)">REJECT</v-btn>
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
  </v-card>
</template>
<script>
import LoanService from '@/services/loan.service'
  export default {
      data () {
          return {
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
              { text: 'Actions', value: 'actions' },
            ],
              search: '',
              loanApplications: [],
              loanApplciation: {},
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
        performAction(item) {
          // perform some action with the item
          console.log(item);
        },
          getLoanApplications(){
              LoanService.getLoanApplications().then(response =>{
                  this.loanApplications = response.data;
              }).catch(error => {
                  console.log('getLoans: ' + error);
                  if(error.response.status === 401 || error.response.status === 403){
                      this.logOut();
                  }
              })
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
          }
      }
  }

</script>