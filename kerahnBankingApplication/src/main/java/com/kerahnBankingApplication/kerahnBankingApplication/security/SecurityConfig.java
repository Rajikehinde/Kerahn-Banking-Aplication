package com.kerahnBankingApplication.kerahnBankingApplication.security;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private UserDetails userDetails;

    public SecurityConfig(UserDetails userDetails){
        this.userDetails= userDetails;
    }

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

//Encryption code
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(customUserDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }


//Authorization code
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

//                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((sessionManagement)-> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize)->
                    authorize
                            .requestMatchers(HttpMethod.POST,"/api/customer/registration").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/admin/registration").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/login").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/api/userFetching").hasAuthority()
                            .requestMatchers(HttpMethod.PUT,"/api/update/user/profile").permitAll()
//                            .requestMatchers("/api/all/customers").permitAll()
                            .requestMatchers("/api/delete").permitAll()
                            .anyRequest().authenticated()
                    )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .authenticationProvider(daoAuthenticationProvider())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }


//Authentication code
//    @Bean
//    public UserDetailsService userDetails() {
//
//        UserDetails user = User.builder()
//                .username("kenny")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("ken")
//                .password(passwordEncoder().encode("password"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,user2);
//    }

}
