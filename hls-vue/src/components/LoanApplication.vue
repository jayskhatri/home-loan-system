<template>
    <v-container>
        <v-row>
            <v-col cols="12" sm="6" md="4">
                <v-select class="camel-case" v-model="loanType" :items="loanTypes" label="Loan Type" outlined></v-select>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="loanAmount" label="Loan Amount" outlined></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field 
                    v-model="loanDuration" 
                    label="Loan Duration" 
                    hint="In years" 
                    outlined>
                </v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="interestRate" outlined disabled>
                    Interest Rate : {{ getInterestRatesByLoanType() }}
                </v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="4" @click="handleDialog">
                <v-dialog v-model="dialog" max-width="500px">
                    <template v-slot:activator="{ on }">
                        <v-text-field hide-details="auto" hint="Start Date" label="Start Date" outlined>{{
                            format(startDate) }}</v-text-field>
                    </template>
                    <v-date-picker v-model="startDate" no-title></v-date-picker>
                    <v-card-actions>
                        <v-btn color="primary" text @click="dialog = false">Cancel</v-btn>
                        <v-btn color="primary" text @click="dialog = false">OK</v-btn>
                    </v-card-actions>
                </v-dialog>
            </v-col>
            <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="interestRate" outlined disabled>
                    End Date : {{ calculateEndDate(startDate, loanDuration) ?? ''}}
                </v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="12" sm="6" md="4" class="d-flex justify-end">
                <v-btn :loading="loading" color="primary" v-if="!saveClicked" @click="saveForLater">Save For Later</v-btn>
                <v-btn :loading="loading" color="primary" v-if="saveClicked" @click="submitLoan">Submit</v-btn>
            </v-col>

        </v-row>
    </v-container>
</template>

<script>
import loanService from '@/services/loan.service';
import UserService from '@/services/user.service';
export default {
    data() {
        return {
            loading: false,
            customer: {},
            submission: {},
            dialog: false,
            saveClicked: false,
            loanType: '',
            loanAmount: '',
            loanDuration: '',
            interestRate: '',
            startDate: null,
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
        };
    },
    computed: {
        currentUser() {
            return this.$store.state.auth.user;
        }
    },
    mounted() {
        if (!this.currentUser) {
            this.$router.push('/login');
        }else{  
            this.getCustomerById(this.$store.state.auth.user.id);
        }
    },
    methods: {
        getInterestRatesByLoanType(){
            let interestRate = '';
            this.interestRatesAccordingToLoanType.forEach(rate => {
                if(rate.type === this.loanType){
                    interestRate = rate.rates;
                }
            });
            return interestRate;
        },
        handleDialog() {
            this.dialog = !this.dialog;
        },
        format(date) {
            if (date === null) return '';
            return date.getDate() + '-' + (date.getMonth() + 1) + '-' + date.getFullYear();
        },
        getCustomerById(id){
            UserService.getCustomerById(id).then(response => {
              this.customer = response.data;
            }).catch(error => {
              console.log('LoanApplication::getCustomerById: ' + this.$store.state.auth.user.id);
            });
        },
        clearVariables(){
            this.loanType = '';
            this.loanAmount = '';
            this.loanDuration = '';
            this.interestRate = '';
            this.startDate = null;
            this.submission = {};
            this.saveClicked = false;
        },
        calculateEndDate(startDate, duration){
            if(startDate === null || duration === '') return '';
            let endDate = new Date(startDate);
            endDate.setFullYear(eval(endDate.getFullYear()) + eval(duration));
            return this.format(endDate);
        },
        saveForLater() {
            this.loading = true;
            console.log('Submitting loan application...');
            console.log(this.customer);
            console.log('Loan Type: ' + this.loanType);
            console.log('Loan Amount: ' + this.loanAmount);
            console.log('Loan Duration: ' + this.loanDuration);
            console.log('Interest Rate: ' + this.getInterestRatesByLoanType());
            console.log('Start Date: ' + this.format(this.startDate));

            setTimeout(() => {
                loanService.applyLoanApplication({
                    personId: this.customer.personId,
                    loanType: this.loanType,
                    loanStatus: 'PENDING',
                    loanAmount: this.loanAmount,
                    loanDuration: this.loanDuration,
                    loanInterestRate: this.getInterestRatesByLoanType(),
                    loanStartDate: this.format(this.startDate),
                    loanEndDate: this.calculateEndDate(this.startDate, this.loanDuration),
                    isSubmitted: false
                }).then(response => {
                    this.submission = response.data;
                    alert('Loan application saved successfully. You can edit/submit it later.');
                    console.log(response.data);
                }).catch(error => {
                    console.error(error);
                });
                this.saveClicked = true;
                this.loading = false;
            }, 2000);
        },
        submitLoan(){
            this.loading = true;
            setTimeout(() => {
                loanService.saveSubmittedLoanApplication(this.submission.loanApplicationId).then(response => {
                    alert('Loan application submitted successfully.');
                    console.log(response.data);
                    this.clearVariables();
                }).catch(error => {
                    console.error(error);
                });
                this.loading = false;
            }, 3000);
        }

    }
};
</script>