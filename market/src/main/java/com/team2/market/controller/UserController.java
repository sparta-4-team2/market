package com.team2.market.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.DefaultResponseDto;
import com.team2.market.dto.orders.response.*;
import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;

import com.team2.market.service.UserService;

import com.team2.market.util.statics.DefaultResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final DefaultResponseEntity responseEntity;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup (@RequestBody SignupRequestDto requestDto) {
        userService.createUser(requestDto);
        return responseEntity.setResponseEntity(null, ResponseMessage.SIGNUP_OK, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login (@RequestBody LoginRequestDto requestDto)
    {
        // login 함수의 반환값 Token을 받아야 하고, 그 토큰을 ResponseEntity를 통해 헤더에 추가해주는 방향으로
        String token = userService.login(requestDto);
        return responseEntity.setResponseEntity(null, ResponseMessage.LOGIN_OK, token, HttpStatus.OK);
    }

	@PostMapping("/users/profile")
	public ResponseEntity<Map<String, Object>> updateProfile(
		@RequestBody ProfileUpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto<UserOrderForm> profileDto = userService.updateProfile(
			requestDto,
			userDetails.getUsername());
		return responseEntity.setResponseEntity(profileDto, ResponseMessage.PROFILE_UPDATE_OK, HttpStatus.OK);
	}

	@GetMapping("/users/profile")
	public ResponseEntity<Map<String, Object>> getProfile(
		@AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto<UserOrderForm> profileDto = userService.getProfile(
			userDetails.getUsername());
		return responseEntity.setResponseEntity(profileDto, ResponseMessage.PROFILE_GET_OK, HttpStatus.OK);
	}

    class ResponseMessage {
        public static final String SIGNUP_OK = "회원가입 성공";
        public static final String LOGIN_OK = "로그인 성공";
        public static final String PROFILE_UPDATE_OK = "프로필 등록 성공";
        public static final String PROFILE_GET_OK = "프로필 등록 성공";
    }
}
