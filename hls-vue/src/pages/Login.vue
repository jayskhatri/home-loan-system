<template>
    <v-container>
        <v-row>
            <v-col cols="12" sm="6" offset-sm="3">
                <v-card>
                    <v-card-title>
                        <h1>Login</h1>
                    </v-card-title>
                    <v-card-text>
                        <v-text-field
                            label="Username"
                            :rules="usernameRules"
                            v-model="username"
                        ></v-text-field>
                        <v-text-field
                            v-model="password"
                            label="Password"
                            :rules="passwordRules"
                            type="password"
                        ></v-text-field>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn color="primary" @click="handleLogin({ username, password })">Login</v-btn>
                      <v-btn color="primary" @click="gotoforgot">Bhul ja</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>
<script>
export default {
    name: "Login",
    data() {
      return {
        loading: false,
        username: '',
        password: '',
        usernameRules: [
          v => !!v || 'Username is required',
        ],
        passwordRules: [
          v => !!v || 'Password is required',
        ],
      };
    },
    computed: {
      loggedIn() {
        console.log('loggedIn: '+ this.$store.state.auth.status.loggedIn);
        return this.$store.state.auth.status.loggedIn;
      },
    },
    created() {
      if (this.loggedIn) {
        this.$router.push("/");
      }
    },
    methods: {
      gotoforgot(){
        this.$router.push("/reset");
      },
      handleLogin(user) {
        this.loading = true;

        this.$store.dispatch('auth/login', user).then(
          () => {
            this.$router.push("/");
          },
          (error) => {
            this.loading = false;
            this.message =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();

              if(error && error.response && error.response.status === 400){
                alert('Invalid Username or Password');
              }
              else console.log('Login.vue :: handleLogin: ' + error);
          }
        );
      },
    },
  };
</script>