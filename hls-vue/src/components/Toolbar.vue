<template>
    <div>
      <v-toolbar
        image="https://cdn.vuetifyjs.com/images/backgrounds/vbanner.jpg"
        dark
        prominent
      >
        <v-app-bar-nav-icon></v-app-bar-nav-icon>
  
        <v-toolbar-title>HLS System</v-toolbar-title>
  
        <v-spacer></v-spacer>
  
        <v-avatar color="info">
            <v-icon icon="mdi-account-circle"></v-icon>
        </v-avatar>

      </v-toolbar>
    </div>
  </template>

  <script>
  export default {
      data: () => ({
        user: {
          initials: 'JD',
          fullName: 'John Doe',
          email: 'john.doe@doe.com',
        },
      }),
    }
    </script>