package com.team2.market.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.DefualtResponseDto;
import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;
import com.team2.market.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<DefualtResponseDto<Void>> signup (@RequestBody SignupRequestDto requestDto) {
        userService.createUser(requestDto);
        return setResponseEntity(null, ResponseMessage.SIGNUP_OK, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<DefualtResponseDto<Void>> login (@RequestBody LoginRequestDto requestDto,
                                    HttpServletResponse response)
    {
        // login 함수의 반환값 Token을 받아야 하고, 그 토큰을 ResponseEntity를 통해 헤더에 추가해주는 방향으로
        String token = userService.login(requestDto, response);
        return setResponseEntity(null, ResponseMessage.LOGIN_OK, token, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ProfileGetResponseDto updateProfile(@RequestBody ProfileUpdateRequestDto requestDto,
                                                    // 변경해야됨
                                                  String request) 
    {
        return userService.updateProfile(requestDto, requestDto.getNickName());
    }

    
    @GetMapping("/profile")
    public ProfileGetResponseDto getProfile(/*변경해야되는 사항*/@RequestBody ProfileUpdateRequestDto requestDto,
                                                  HttpServletRequest request) 
    {
        return userService.getProfile(requestDto.getNickName());
    }

    private <T> ResponseEntity<DefualtResponseDto<T>> setResponseEntity(T data, String msg, HttpStatus status) {
        DefualtResponseDto<T> defaultResponse = new DefualtResponseDto<>(status.value(), msg, data);
        ResponseEntity<DefualtResponseDto<T>> ret = new ResponseEntity<>(defaultResponse, status);
        return ret;
    }
    
    private <T> ResponseEntity<DefualtResponseDto<T>> setResponseEntity(T data, String msg, String token, HttpStatus status) {
        DefualtResponseDto<T> defaultResponse = new DefualtResponseDto<>(status.value(), msg, data);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        ResponseEntity<DefualtResponseDto<T>> ret = new ResponseEntity<>(defaultResponse, headers, status);
        return ret;
    }

    class ResponseMessage {
        public static final String SIGNUP_OK = "회원가입 성공";
        public static final String LOGIN_OK = "로그인 성공";
    }
}
