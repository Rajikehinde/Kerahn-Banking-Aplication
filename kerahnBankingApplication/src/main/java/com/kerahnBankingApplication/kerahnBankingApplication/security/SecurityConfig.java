package com.kerahnBankingApplication.kerahnBankingApplication.security;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//Encryption code
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//Authorization code
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize)->
                    authorize
                            .requestMatchers(HttpMethod.POST,"/api/customer/registration").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/admin/registration").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/login").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/userFetching").permitAll()
                            .requestMatchers(HttpMethod.PUT,"/api/update/user/profile").permitAll()
                            .requestMatchers("/api/all/customers").permitAll()
                            .requestMatchers("/api/delete").permitAll()
                            .anyRequest().authenticated()
                    )
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
