<template>
    <v-table>
      <thead>
        <tr>
          <th class="text-left">
            ID
          </th>
          <th class="text-left">
            Username
          </th>
          <th class="text-left">
            Loan Type
          </th>
          <th class="text-left">
            Loan Amount
          </th>
          <th class="text-left">
            Loan Duration
          </th>
          <th class="text-left">
            Loan Status
          </th>
          <th class="text-left">
            Interest
          </th>
          <th class="text-left">
            start date
          </th>
          <th class="text-left">
            end date
          </th>
          <th class="text-left">
            Actions
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="application in loanApplications"
          :key="application.loanApplicationId"
        >
          <td>{{ application.loanApplicationId }}</td>
          <td>{{ application.personId }}</td>
          <td>{{ application.loanType }}</td>
          <td>{{ application.loanAmount }}</td>
          <td>{{ application.loanDuration }}</td>
          <td>{{ application.loanStatus }}</td>
          <td>{{ application.loanInterestRate }}</td>
          <td>{{ application.loanStartDate }}</td>
          <td>{{ application.loanEndDate }}</td>
          <td>
            <v-btn
              color="red"
              @click="rejectLoan(application.loanApplicationId)"
            >
              Reject
            </v-btn>
            <v-btn
              color="green"
              @click="approveLoan(application.loanApplicationId)"
            >
              Approve
            </v-btn>
          </td>
        </tr>
      </tbody>
    </v-table>
  </template>
  <script>
  import LoanService from '@/services/loan.service'
    export default {
        data () {
            return {
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
                LoanService.approveLoanApplication(id).then(response =>{
                    this.getLoanApplications();
                }).catch(error => {
                    console.log('approveLoan: ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                })
            },

            rejectLoan(id){
                LoanService.rejectLoanApplication(id).then(response =>{
                    this.getLoanApplications();
                }).catch(error => {
                    console.log('rejectLoan: ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                })
            }
        }
    }

  </script>