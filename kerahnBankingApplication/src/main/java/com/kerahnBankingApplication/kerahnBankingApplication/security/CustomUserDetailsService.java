package com.kerahnBankingApplication.kerahnBankingApplication.security;

import com.kerahnBankingApplication.kerahnBankingApplication.entity.Customer;
import com.kerahnBankingApplication.kerahnBankingApplication.entity.Roles;
import com.kerahnBankingApplication.kerahnBankingApplication.repository.CustomerRepository;
import com.kerahnBankingApplication.kerahnBankingApplication.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Customer customer = customerRepository.findByUsername(username)
                    .orElseThrow(()-> new UsernameNotFoundException("User with provided credentials not found!" + username));

            //retrieve roles associated with the user
            //granted authority is what we used for mapping a role to a user
//            Roles roles = roleRepository.findByRoleName("USER").orElseThrow();
            Set<GrantedAuthority> authorities = customer.getRoles().stream()
                    .map((role)-> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toSet());

            return new User(customer.getEmail(),customer.getPassword(),authorities);
}
    }

