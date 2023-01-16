package com.team2.market.service;

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
    public LoginResponseDto login(LoginRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProfileUpdateResponseDto updateProfile(ProfileUpdateRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProfileGetResponseDto getProfile(ProfileGetRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

}
