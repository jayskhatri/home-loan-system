<template>
    <v-card>
      <v-tabs
        v-model="tab"
        align-tabs="center"
        bg-color="secondary"
      >
      <v-tab v-if="isAdminUser" value="userDashboard">User Dashboard</v-tab>
        <v-tab value="loanApplications">{{this.isAdminUser ? 'Loan Applications' : 'Apply for loan'}}</v-tab>
        <v-tab value="myProfile">My Profile</v-tab>
      </v-tabs>
  
      <v-card-text>
        <v-tabs-window v-model="tab">
          <v-tabs-window-item value="loanApplications">
            <LoanDashboard />
          </v-tabs-window-item>
  
          <v-tabs-window-item v-if="isAdminUser" value="userDashboard">
            <UserDashboard />
          </v-tabs-window-item>
  
          <v-tabs-window-item value="myProfile">
            <MyProfile />
          </v-tabs-window-item>
        </v-tabs-window>
      </v-card-text>
    </v-card>
</template>


<script>
export default {
    data: () => ({
        tab: null,
    }),
    computed: {
        currentUser() {
            return this.$store.state.auth.user;
        },
        isAdminUser(){
          if(this.currentUser === null) return false;
          var roles = this.$store.state.auth.user.roles;
          return roles.includes('ADMIN');
        }
    },
    mounted() {
        if (this.currentUser === null) {
          this.$router.push('/login');
        }
    },
    methods: {
        logOut() {
            this.$store.dispatch('auth/logout');
            this.$router.push('/login');
        }
    }
}
</script>

<style scoped>
    .fab {
    position: fixed;
    right: 20px;
    bottom: 50px;
    z-index: 2;
    }
</style>