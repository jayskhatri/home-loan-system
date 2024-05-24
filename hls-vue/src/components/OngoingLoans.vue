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
          <div class="text-center">Actions</div>
        </template>

        <template v-slot:item.actions="{ item }">
          <div class="d-flex justify-center">
            <v-btn :disabled="item.isSubmitted" color="yellow" @click="editLoanApplication(item)">EDIT</v-btn>
            <v-btn :disabled="item.isSubmitted" color="red" @click="deleteLoanApplication(item.loanApplicationId)">DELETE</v-btn>
            <v-btn :disabled="item.isSubmitted" color="green" @click="submitLoanApplication(item.loanApplicationId)">SUBMIT</v-btn>
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
        <v-container>
          <v-row>
            <v-col cols="12" sm="6" md="4">
                <v-select class="camel-case" v-model="loanType" :items="loanTypes" label="Loan Type" outlined></v-select>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="loanAmount" label="Loan Amount" outlined></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="loanDuration" label="Loan Duration" hint="In years" outlined></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="interestRate" outlined disabled>
                    Interest Rate : {{ getInterestRatesByLoanType() }}
                </v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="startDate" :value="startDate" label="Start Date" type="date">
                </v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="endDate" outlineddisabled> End Date: {{ formattedEndDate }} </v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="12" sm="6" md="4" class="d-flex justify-end">
                <v-btn :loading="loading" color="primary" v-if="!saveClicked" @click="updateApplication">Update</v-btn>
            </v-col>
        </v-row>
        </v-container>
      </v-card>   
    </v-dialog>
  </template>

  <script>
  import LoanService from '@/services/loan.service'
  import { format } from 'date-fns';
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
                loanApplications: [],
                loanApplication: {},
                dialog: false,
                loanTypes: ['HOME_LOAN', 'CAR_LOAN', 'PERSONAL_LOAN', 'EDUCATION_LOAN'],
                interestRatesAccordingToLoanType: [
                    {
                        type: 'HOME_LOAN',
                        rates: '7%'
                    }, 
                    {
                        type: 'CAR_LOAN',
                        rates:'8%'
                    }, 
                    {
                        type: 'PERSONAL_LOAN',
                        rates: '10%'
                    }, 
                    {
                        type: 'EDUCATION_LOAN',
                        rates: '6%'
                    }
                ],
                loanType: '',
                loanAmount: '',
                loanDuration: '',
                interestRate: '',
                startDate: '',
                endDate: '',
                appID: '',
            }
        },
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            },
            formattedEndDate() {
                if(this.startDate === '' || this.loanDuration === '') return '-';
                var endD = new Date(this.startDate);
                endD.setFullYear(endD.getFullYear() + parseInt(this.loanDuration));
                return format(endD, 'dd/MM/yyyy');
            },
            formattedStartDate() {
                var startDate = new Date(this.startDate);
                this.endDate = this.formattedEndDate;
                return format(startDate, 'dd/MM/yyyy');
            },
        },
        mounted() {
          if (!this.currentUser) {
            this.$router.push('/login');
          }
          this.getLoanApplications();
        },
        methods: {
            getInterestRatesByLoanType(){
                let interestRate = '-';
                this.interestRatesAccordingToLoanType.forEach(rate => {
                    if(rate.type === this.loanType){
                        interestRate = rate.rates;
                    }
                });
                return interestRate;
            },
            getLoanApplications(){
                LoanService.getLoanApplicationsByPersonID(this.$store.state.auth.user.id).then(response =>{
                    this.loanApplications = response.data;
                }).catch(error => {
                    console.log('getLoanApplications(): ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                })
            },

            editLoanApplication(application){
                this.appID = application.loanApplicationId;
                this.loanType = application.loanType;
                this.loanAmount = application.loanAmount;
                this.loanDuration = application.loanDuration;
                const [day, month, year] = application.loanStartDate.split('/');
                this.startDate = `${year}-${month}-${day}`
                this.dialog = !this.dialog;
            },

            updateApplication(){
                this.loanApplication = this.loanApplications.find(app => app.loanApplicationId === this.appID);

                this.loanApplication.loanType = this.loanType;
                this.loanApplication.loanAmount = this.loanAmount;
                this.loanApplication.loanDuration = this.loanDuration;
                this.loanApplication.loanInterestRate = this.getInterestRatesByLoanType();
                this.loanApplication.loanStartDate = this.formattedStartDate;
                this.loanApplication.loanEndDate = this.formattedEndDate;
           
                LoanService.updateLoanApplication(this.appID, {
                    loanApplicationId: this.appID,
                    personId: this.loanApplication.personId,
                    loanType: this.loanApplication.loanType,
                    loanStatus: this.loanApplication.loanStatus,
                    loanAmount: this.loanApplication.loanAmount,
                    loanDuration: this.loanApplication.loanDuration,
                    loanInterestRate: this.loanApplication.loanInterestRate,
                    loanStartDate: this.loanApplication.loanStartDate,
                    loanEndDate: this.loanApplication.loanEndDate,
                    isSubmitted: false
                }).then(response => {
                    this.dialog = !this.dialog;
                }).catch(error => {
                    this.dialog = !this.dialog;
                    console.log('updateApplication(): ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                });
            },

            deleteLoanApplication(id){
              if (confirm("Do you want to delete the loan application permanently?")) {
                LoanService.deleteLoanApplicationByID(id).then(response =>{
                    this.getLoanApplications();
                }).catch(error => {
                    alert("Something went wrong. Unable to delete the loan application.");
                    console.log('deleteLoanApplication(): ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                });
              }
            },
            submitLoanApplication(id){
                LoanService.saveSubmittedLoanApplication(id).then(response => {
                    this.getLoanApplications();
                }).catch(error => {
                    console.log('submitLoanApplication(): ' + error);
                    if(error.response.status === 401 || error.response.status === 403){
                        this.logOut();
                    }
                });
            }
        }
    }

  </script>