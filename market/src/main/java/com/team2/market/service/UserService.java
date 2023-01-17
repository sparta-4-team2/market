package com.team2.market.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.market.entity.User;
import com.team2.market.entity.UserRoleEnum;
import org.springframework.stereotype.Service;


import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;
import com.team2.market.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void createUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        UserRoleEnum role = UserRoleEnum.BUYER;
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    @Override
    public void login(LoginRequestDto requestDto, HttpServletResponse request) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("등록된 사용자가 없습니다."));

        if (!user.isValidPassword(requestDto.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtService.AUTHORIZATION_HEADER, jwtService.createToken(user.getUsername(), user.getRole()));
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
