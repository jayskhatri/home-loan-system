<template>
    <v-card >
        <v-container>
            <v-row>
                <v-col cols="2">
                    <v-avatar image="../assets/images.jpeg" size="70">
                    </v-avatar>
                </v-col>
                <v-col cols="8">
                    <v-card-title>Hi {{customer.firstName}}!</v-card-title>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
    <v-divider
        :thickness="7"
        class="border-opacity-100"
        color="success"
        inset
    ></v-divider>
   
    <v-card>
        <v-container>
            <v-row >
                <v-col cols="2"> Name: </v-col>
                <v-col cols="6">
                    <v-text-field v-model="customer.firstName" variant="underlined" readonly></v-text-field>
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="2"> Username: </v-col>
                <v-col cols="6">
                    <v-text-field v-model="customer.username" variant="underlined" readonly></v-text-field>
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="2"> Email: </v-col>
                <v-col cols="6">
                    <v-text-field v-model="customer.email" variant="underlined" readonly></v-text-field>
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="2"> Address: </v-col>
                <v-col cols="6">
                    <v-text-field v-model="customer.address" variant="underlined" readonly></v-text-field>
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="2"> Contact: </v-col>
                <v-col cols="6">
                    <v-text-field v-model="customer.phoneNumber" variant="underlined" readonly></v-text-field>
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="2"> DOB: </v-col>
                <v-col cols="6">
                    <v-text-field v-model="customer.dateOfBirth" variant="underlined" readonly></v-text-field>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
</template>

<script>
import axios from 'axios';
import UserService from "@/services/user.service";
export default {
    data: () => ({
        token: '',
        customers: [],
        customer: {},
        dialog: false   
    }),
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
        getCustomerById(id){
            UserService.getCustomerById(id).then(response => {
              this.customer = response.data;
            }).catch(error => {
              console.log('getCustomerById: ' + this.$store.state.auth.user.id);
            });
        },
    }
}
</script>

<style>
/* Add custom styles here if needed */
</style>
