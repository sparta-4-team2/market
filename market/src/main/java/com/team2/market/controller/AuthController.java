package com.team2.market.controller;

import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.auth.response.AuthChangeResponseDto;
import com.team2.market.dto.auth.response.RequestAuthResponseDto;
import com.team2.market.service.AuthService;
import com.team2.market.util.statics.DefaultResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    /** 구매자의 판매자 권한 요청
     * 
     */
    @PostMapping("/auth/request")
    public ResponseEntity<Map<String, Object>> requestAuth(@AuthenticationPrincipal UserDetails user) {
        RequestAuthResponseDto data = authService.requestAuthorization(user);

        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.AUTHREQUEST_OK, HttpStatus.OK);
    }

    /** 운영자의 구매자 권한 요청 목록 확인
     * 
     */
    @GetMapping("/auth/request") 
    public ResponseEntity<Map<String, Object>> getAllAuthRequest() {
        List<RequestAuthResponseDto> data = authService.getAllRequset();

        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.GETREQUEST_OK, HttpStatus.OK);
    }

    // Post나 Put 메소드 뭘 써야할지 잘몰라서 일시적으로 Post로 작성
    // 운영자 권한 판단 넣어줄 것
    @PostMapping("/auth/request/{requestId}")
    public ResponseEntity<Map<String, Object>> changeAuth(@PathVariable Long requestId) {
        AuthChangeResponseDto data = authService.changeAuthorization(requestId);
        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.AUTHCHANGE_OK, HttpStatus.OK);
    }


    @GetMapping("/seller")
    public void getAllSellers() {
        authService.getAllSellers();
    }

    @GetMapping("/user")
    public void getAllUsers() {
        authService.getCustomInfo();
    }

    @GetMapping("/auth/seller/{sellerid}")
    public void getMethodName() {
        authService.getSellerInfo();
    }
    
    class ResponseMessage {
        public static final String AUTHREQUEST_OK = "판매자 권한 요청 성공";
        public static final String GETREQUEST_OK = "일반 고객의 권한 요청 목록 조회 성공";
        public static final String AUTHCHANGE_OK = "권한 변경 성공";
    }
}
