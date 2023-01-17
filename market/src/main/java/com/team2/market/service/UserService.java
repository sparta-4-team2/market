package com.team2.market.service;

import javax.servlet.http.HttpServletRequest;
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
