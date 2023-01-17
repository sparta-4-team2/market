package com.team2.market.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.market.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public void requestAuth() {
        authService.requestAuthorization();
    }

    @GetMapping("/seller")
    public void getAllSellers() {
        authService.getAllSellers();
    }

    @GetMapping("/user")
    public void getAllUsers() {
        authService.getCustomInfo();
    }

    @GetMapping("/auth/seller") 
    public void getAllAuthRequest() {

    }

    // Post나 Put 메소드 뭘 써야할지 잘몰라서 일시적으로 Post로 작성
    @PostMapping("/auth/seller/{userid}")
    public void changeAuth() {
        authService.setAuthorization();
    }

    @GetMapping("/auth/seller/{sellerid}")
    public void getMethodName() {
        authService.getSellerInfo();
    }
    
}
