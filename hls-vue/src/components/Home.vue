<template>
    <v-card>
      <v-tabs
        v-model="tab"
        align-tabs="center"
        bg-color="secondary"
      >
        <v-tab value="userDashboard">User Dashboard</v-tab>
        <v-tab value="loanApplications">Loan Applications</v-tab>
        <v-tab value="myProfile">My Profile</v-tab>
      </v-tabs>
  
      <v-card-text>
        <v-tabs-window v-model="tab">
          <v-tabs-window-item value="loanApplications">
            One
          </v-tabs-window-item>
  
          <v-tabs-window-item value="userDashboard">
            <UserDashboard />
          </v-tabs-window-item>
  
          <v-tabs-window-item value="myProfile">
            Three
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
        }
    },
    mounted() {
        if (!this.currentUser) {
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