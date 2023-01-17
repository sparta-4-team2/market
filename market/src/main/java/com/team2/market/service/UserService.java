package com.team2.market.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;
import com.team2.market.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;

    @Override
    public SignupResponseDto createUser(SignupRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProfileUpdateResponseDto updateProfile(ProfileUpdateRequestDto requestDto, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProfileGetResponseDto getProfile(ProfileGetRequestDto requestDto, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }


}
