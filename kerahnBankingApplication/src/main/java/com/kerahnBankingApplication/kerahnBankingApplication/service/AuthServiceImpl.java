package com.kerahnBankingApplication.kerahnBankingApplication.service;

import com.kerahnBankingApplication.kerahnBankingApplication.dto.LoginDto;
import com.kerahnBankingApplication.kerahnBankingApplication.entity.Customer;
import com.kerahnBankingApplication.kerahnBankingApplication.repository.CustomerRepository;
import com.kerahnBankingApplication.kerahnBankingApplication.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl {

    private CustomerRepository customerRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    public AuthServiceImpl(CustomerRepository customerRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public AuthResponse login (LoginDto loginDto){

        Customer customer = customerRepository.findByUsername(loginDto.getUsername()).orElseThrow(()-> new UsernameNotFoundException("userName not found"));
        log.info("found the customer " + customer.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getUsername(),loginDto.getPassword()));
        log.info("Authenticate the customer " + customer);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtTokenProvider.generateToken(authentication));
        return authResponse;



//        private AuthenticationManager authenticationManager;
//        private UserRepository userRepository;
//        private PasswordEncoder passwordEncoder;
//        private JwtTokenProvider jwtTokenProvider;
//
//    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
//            this.authenticationManager = authenticationManager;
//            this.userRepository = userRepository;
//            this.passwordEncoder = passwordEncoder;
//            this.jwtTokenProvider = jwtTokenProvider;
//        }
//
//        @Override
//        public AuthResponse login(LoginDto loginDto) {
//            //authenticating user here
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            AuthResponse authResponse = new AuthResponse();
//            authResponse.setToken(jwtTokenProvider.generateToken(authentication));
//            return authResponse;
//        }







//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            AuthResponse authResponse = new AuthResponse();
//            authResponse.setToken(jwtTokenProvider.generateToken(authentication));
//            return authResponse;
//        }
    }
}
