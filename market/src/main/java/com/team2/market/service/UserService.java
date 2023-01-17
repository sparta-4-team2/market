package com.team2.market.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.LoginResponseDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.dto.users.response.SignupResponseDto;
import com.team2.market.entity.User;
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
    @Transactional
    @Override
    public ProfileGetResponseDto updateProfile(ProfileUpdateRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "세부사항 후에 추가"));
        user.updateProfile(requestDto);
        return new ProfileGetResponseDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileGetResponseDto getProfile(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "세부사항 후에 추가"));
        return new ProfileGetResponseDto(user);
    }


}
