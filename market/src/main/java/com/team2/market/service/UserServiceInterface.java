package com.team2.market.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;

public interface UserServiceInterface {
    void createUser(SignupRequestDto requestDto);
    void login(LoginRequestDto requestDto, HttpServletResponse request);

    ProfileGetResponseDto updateProfile(ProfileUpdateRequestDto requestDto, String username);
    ProfileGetResponseDto getProfile(String username);
}
