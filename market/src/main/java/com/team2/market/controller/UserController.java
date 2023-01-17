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

import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.LoginResponseDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.dto.users.response.SignupResponseDto;
import com.team2.market.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public SignupResponseDto signup(@RequestBody SignupRequestDto requestDto) {
		return userService.createUser(requestDto);
	}

	@PostMapping("/login")
	public LoginResponseDto login(@RequestBody LoginRequestDto requestDto,
		HttpServletResponse response) {
		return userService.login(requestDto, response);
	}

	@PostMapping("/profile")
	public ResponseEntity<ProfileGetResponseDto> updateProfile(@RequestBody ProfileUpdateRequestDto requestDto,
		@AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto profileDto = userService.updateProfile(requestDto,
			userDetails.getUsername());
		return ResponseEntity.ok(profileDto);
	}

	@GetMapping("/profile")
	public ResponseEntity<ProfileGetResponseDto> getProfile(
		@AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto profileDto = userService.getProfile(userDetails.getUsername());
		return ResponseEntity.ok(profileDto);
	}
}
