package com.team2.market.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.DefaultResponseDto;
import com.team2.market.dto.auth.*;
import com.team2.market.service.AuthService;
import com.team2.market.util.statics.DefaultResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private final DefaultResponseEntity responseEntity;

    /** 구매자의 판매자 권한 요청
     * 
     */
    @PostMapping("/auth/request")
    public ResponseEntity<DefaultResponseDto<RequestAuthResponseDto>> requestAuth(@AuthenticationPrincipal UserDetails user) {
        RequestAuthResponseDto data = authService.requestAuthorization(user);

        return responseEntity.setResponseEntity(data, ResponseMessage.AUTHREQUEST_OK, HttpStatus.OK);
    }

    /** 운영자의 구매자 권한 요청 목록 확인
     * 
     */
    @GetMapping("/auth/seller") 
    public void getAllAuthRequest() {
    }


    @GetMapping("/seller")
    public void getAllSellers() {
        authService.getAllSellers();
    }

    @GetMapping("/user")
    public void getAllUsers() {
        authService.getCustomInfo();
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
    
    class ResponseMessage {
        public static final String AUTHREQUEST_OK = "판매자 권한 요청 성공";
    }
}
