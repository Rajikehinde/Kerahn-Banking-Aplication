package com.kerahnBankingApplication.kerahnBankingApplication.security;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize)-> {
                    authorize
                            .requestMatchers("/api/customer/registration").permitAll()
                            .requestMatchers("/api/admin/registration").permitAll()
                            .requestMatchers("/api/userFetching").hasRole("ADMIN")
                            .requestMatchers("/api/update/user").hasRole("ADMIN")
//                            .requestMatchers("/api/all/customers").authenticated()
                            .requestMatchers("/api/delete").hasRole("ADMIN")
                            .anyRequest().authenticated();
                    })
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }


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
