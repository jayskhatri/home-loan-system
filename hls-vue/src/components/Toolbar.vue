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
  
        <v-menu
        min-width="200px"
        rounded
      >
        <template v-slot:activator="{ props }">
          <v-btn
            icon
            v-bind="props"
          >
            <v-avatar
              :color="randomColor"
              size="large"
            >
              <span class="text-h5">{{ user.initials }}</span>
            </v-avatar>
          </v-btn>
        </template>
        <v-card>
          <v-card-text>
            <div class="mx-auto text-center">
              <v-avatar
                color="brown"
              >
                <span class="text-h5">{{ user.initials }}</span>
              </v-avatar>
              <h3>{{ user.fullName }}</h3>
              <p class="text-caption mt-1">
                {{ user.email }}
              </p>
              <v-divider class="my-3"></v-divider>
              <v-btn
                variant="text"
                rounded
                @click="editAccount"
              >
                Edit Account
              </v-btn>
              <v-divider class="my-3"></v-divider>
              <v-btn
                variant="text"
                rounded
                @click="logOut"
              >
                Logout
              </v-btn>
            </div>
          </v-card-text>
        </v-card>
      </v-menu>

      </v-toolbar>
    </div>
  </template>

  <script>
  import userService from '@/services/user.service';
  export default {
      data: () => ({
        user: {
          initials: 'JD',
          fullName: 'John Doe',
          email: 'john.doe@doe.com',
        },
      }),
      computed: {
          currentUser() {
              return this.$store.state.auth.user;
          }
      },
      mounted() {
          this.initialSetup();
      },
      methods:{
          async initialSetup(){
              if(this.currentUser){
                  try{
                    const response = await userService.getCustomerById(this.currentUser.id);
                    this.user.initials = response.data.firstName.charAt(0).toUpperCase() + response.data.lastName.charAt(0).toUpperCase();
                    this.user.fullName = response.data.firstName + ' ' + response.data.lastName;
                    this.user.email = response.data.email;
                  }catch(error){
                      console.log(error);
                  }
              }
          },
          randomColor(){
              return `#${Math.floor(Math.random()*16777215).toString(16)}`;
          },
          editAccount() {
              alert('Feature coming soon');
          },
          logOut() {
              this.$store.dispatch('auth/logout');
              this.$router.push('/login');
          }
       }
    }
    </script>