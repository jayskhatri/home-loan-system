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
      <!-- <v-tab value="four">
        <v-avatar color="info">
          <v-icon icon="mdi-account-circle"></v-icon>
        </v-avatar>
      </v-tab> -->
    </v-tabs>

    <v-card-text>
      <v-tabs-window v-model="tab">
        <v-tabs-window-item value="loanApplications">
          One
        </v-tabs-window-item>

        <v-tabs-window-item value="userDashboard">
          <LoanApplications />
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
  }
</script>
