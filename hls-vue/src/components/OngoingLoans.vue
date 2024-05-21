<template>
    <v-card flat>
      <v-card-title class="d-flex align-center pe-2">
        <v-icon icon="mdi-video-input-component"></v-icon> &nbsp;
        My Loan Applications

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
          <div>Application Status</div>
        </template>

        <template v-slot:header.actions="{ header }">
          <div class="text-center">Take Action</div>
        </template>

        <template v-slot:item.actions="{ item }">
          <div class="d-flex justify-center">
            <v-btn :disabled="item.isSubmitted" color="green" @click="editLoanApplication(item.loanApplicationId)">EDIT</v-btn>
            <v-btn :disabled="item.isSubmitted" color="red" @click="deleteLoanApplication(item.loanApplicationId)">DELETE</v-btn>
          </div>
        </template>

        <template v-slot:item.isSubmitted="{ item }">
          <v-chip
            :color="item.isSubmitted === true ? 'green' : 'red'"
            :text="item.isSubmitted === true ? 'SUBMITTED' : 'PENDING'"
            class="text-uppercase"
            size="small"
            label
          ></v-chip>
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

    <v-dialog  v-model="dialog" max-width="1000">
      <v-card>
        <LoanApplication />
      </v-card>
    </v-dialog>
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
                { text: 'Submitted?', value: 'isSubmitted'},
                { text: 'Actions', value: 'actions' },
              ],
                search: '',
                customer: {},
                loanApplications: [],
                loanApplciation: {},
                dialog: false,
                update: true,
                loanTypes: ['HOME_LOAN', 'CAR_LOAN', 'PERSONAL_LOAN', 'EDUCATION_LOAN'],
                interestRates: ['7%']
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
                console.log('Getting loan applications...');
                console.log('Person ID: ' + this.$store.state.auth.user.id);
                LoanService.getLoanApplicationsByPersonID(this.$store.state.auth.user.id).then(response =>{
                    this.loanApplications = response.data;
                    console.log(this.loanApplications);
                }).catch(error => {
                    console.log('getLoanApplications(): ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                })
            },

            editLoanApplication(id){
                this.dialog = !this.dialog;
            },

            deleteLoanApplication(id){
              if (confirm("Do you want to delete the loan application permanently?")) {
                LoanService.deleteLoanApplicationByID(id).then(response =>{
                    this.getLoanApplications();
                }).catch(error => {
                    alert("Something went wrong. Unable to delete the loan application.");
                    console.log('deleteLoanApplication: ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                });
              }
            }
        }
    }

  </script>