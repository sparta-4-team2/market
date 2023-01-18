package com.team2.market.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.market.dto.orders.response.UserOrderForm;
import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public String signup(@RequestBody SignupRequestDto requestDto) {
		userService.createUser(requestDto);
		return "회원가입 성공";
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequestDto requestDto,
		HttpServletResponse response) {
		userService.login(requestDto, response);
		return "로그인 성공";
	}

	@PostMapping("/users/profile")
	public ResponseEntity<ProfileGetResponseDto<UserOrderForm>> updateProfile(
		@RequestBody ProfileUpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto<UserOrderForm> profileDto = userService.updateProfile(
			requestDto,
			userDetails.getUsername());
		return ResponseEntity.ok(profileDto);
	}

	@GetMapping("/users/profile")
	public ResponseEntity<ProfileGetResponseDto<UserOrderForm>> getProfile(
		@AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto<UserOrderForm> profileDto = userService.getProfile(
			userDetails.getUsername());
		return ResponseEntity.ok(profileDto);
	}

}
