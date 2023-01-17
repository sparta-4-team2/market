package com.team2.market.service;

import javax.servlet.http.HttpServletResponse;

import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.LoginResponseDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.dto.users.response.SignupResponseDto;

public interface UserServiceInterface {
    SignupResponseDto createUser(SignupRequestDto requestDto);
    LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse request);
    ProfileGetResponseDto updateProfile(ProfileUpdateRequestDto requestDto, String username);
    ProfileGetResponseDto getProfile(String username);
}
