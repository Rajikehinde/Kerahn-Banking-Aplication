package com.kerahnBankingApplication.kerahnBankingApplication.controller;

import com.kerahnBankingApplication.kerahnBankingApplication.dto.LoginDto;
import com.kerahnBankingApplication.kerahnBankingApplication.service.AuthResponse;
import com.kerahnBankingApplication.kerahnBankingApplication.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;
    @PostMapping("/login")
    public AuthResponse login (@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }
}
