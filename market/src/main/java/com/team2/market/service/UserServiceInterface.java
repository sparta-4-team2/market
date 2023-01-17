package com.team2.market.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;

public interface UserServiceInterface {
    SignupResponseDto createUser(SignupRequestDto requestDto);
    LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse request);
    ProfileUpdateResponseDto updateProfile(ProfileUpdateRequestDto requestDto, HttpServletRequest request);
    ProfileGetResponseDto getProfile(ProfileGetRequestDto requestDto, HttpServletRequest request);
}
