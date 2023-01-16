package com.team2.market.service;

import org.springframework.stereotype.Service;

import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.LoginResponseDto;
import com.team2.market.dto.users.response.SignupResponseDto;

@Service
public class UserService implements UserServiceInterface{

    @Override
    public SignupResponseDto createUser(SignupRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

}
