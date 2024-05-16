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
            <v-icon icon="mdi-account-circle" @click ="logOut"></v-icon>
        </v-avatar>

      </v-toolbar>
    </div>
  </template>

  <script>
  export default {
      name: 'Toolbar',
      data: () => ({
      }),
      methods:{
        logOut() {
            this.$store.dispatch('auth/logout');
            this.$router.push('/login');
        }
      }
    }
    </script>