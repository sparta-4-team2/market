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
}
