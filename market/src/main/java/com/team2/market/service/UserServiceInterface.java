package com.team2.market.service;

import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.LoginResponseDto;
import com.team2.market.dto.users.response.SignupResponseDto;

public interface UserServiceInterface {
    public SignupResponseDto createUser(SignupRequestDto requestDto);
    public LoginResponseDto login(LoginRequestDto requestDto);
}
