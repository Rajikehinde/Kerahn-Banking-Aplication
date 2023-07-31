package com.kerahnBankingApplication.kerahnBankingApplication.security;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Customer;
import com.kerahnBankingApplication.kerahnBankingApplication.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customer = Optional.ofNullable(Optional.ofNullable(customerRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("Email not found")));

//        Set<GrantedAuthority> authorities = customer.getRoles().stream()
//                .map((role)-> new SimpleGrantedAuthority(role.getRoleName().toString())).collect(Collectors.toSet());
//        return new User(customer.getEmail(), customer.getPassword(),authorities);

        return customer.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"+ email));
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Patients> userInfo = Optional.ofNullable(patientRepository.findByEmail(email).orElseThrow(() ->
//                new UsernameNotFoundException("User with provided information not found")));
//
//
//
//        return userInfo.map(UserInfoUserDetails::new)
//                .orElseThrow(()-> new UsernameNotFoundException("User not found"+ email));
//    }
}
