package com.team2.market.controller;

import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.SignupResponseDto;
import com.team2.market.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponseDto signup (@RequestBody SignupRequestDto requestDto) {
        return userService.createUser(requestDto);
    }
}
