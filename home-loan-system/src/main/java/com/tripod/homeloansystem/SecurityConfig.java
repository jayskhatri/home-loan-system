package com.tripod.homeloansystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tripod.homeloansystem.exceptions.ResourceNotFoundException;
import com.tripod.homeloansystem.jwt.AuthEntryPointJwt;
import com.tripod.homeloansystem.jwt.AuthTokenFilter;
import com.tripod.homeloansystem.repositories.PersonRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private PersonRepository personRepository;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorizereq -> 
            authorizereq.requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/api/v1/login").permitAll()
            .requestMatchers("/api/v1/users/init").permitAll()
            .requestMatchers("/api/v1/refreshtoken").permitAll()
            .anyRequest().authenticated()
        );

        http.sessionManagement(session->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.exceptionHandling(exception ->
            exception.authenticationEntryPoint(unauthorizedHandler)
        );

        http.headers(headers ->
            headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
        );

        http.csrf(csrf -> csrf.disable());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> personRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("Person with this username is not found"));
    }

    // @Bean
    // public CommandLineRunner initData(UserDetailsService userDetailsService){
    //     return args -> {
    //         // UserDetails user1 = User.withUsername("user").password(passwordEncoder().encode("user")).roles("USER").build();
    //         UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();

    //         // JdbcUserDetailsManager userDetailsManager = (JdbcUserDetailsManager) userDetailsService;
    //         // userDetailsManager.createUser(admin);
    //         // userDetailsManager.createUser(user2);
    //     };
    // }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        // return new PasswordEnconderTest();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration builder) throws Exception{
        return builder.getAuthenticationManager();
    }
}
