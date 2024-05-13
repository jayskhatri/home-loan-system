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
import axios from 'axios';
import { ref } from 'vue';

  export default {
    data: () => ({
      tab: null,
    }),
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