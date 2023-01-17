package com.team2.market.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

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
    public String signup (@RequestBody SignupRequestDto requestDto) {
        userService.createUser(requestDto);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login (@RequestBody LoginRequestDto requestDto,
                                    HttpServletResponse response)
    {
        userService.login(requestDto, response);
        return "로그인 성공";
    }

    @PostMapping("/profile")
    public ProfileUpdateResponseDto updateProfile(@RequestBody ProfileUpdateRequestDto requestDto,
                                                  HttpServletRequest request) 
    {
        return userService.updateProfile(requestDto, request);
    }

    
    @GetMapping("/profile")
    public ProfileGetResponseDto getProfile(@RequestBody ProfileGetRequestDto requestDto,
                                                  HttpServletRequest request) 
    {
        return userService.getProfile(requestDto, request);
    }
}
