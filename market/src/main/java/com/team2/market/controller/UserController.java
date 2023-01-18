package com.team2.market.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.market.dto.DefaultResponseDto;
import com.team2.market.dto.orders.response.UserOrderForm;
import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
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
    public ResponseEntity<DefaultResponseDto<Void>> signup (@RequestBody SignupRequestDto requestDto) {
        userService.createUser(requestDto);
        return responseEntity.setResponseEntity(null, ResponseMessage.SIGNUP_OK, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponseDto<Void>> login (@RequestBody LoginRequestDto requestDto,
                                    HttpServletResponse response)
    {
        // login 함수의 반환값 Token을 받아야 하고, 그 토큰을 ResponseEntity를 통해 헤더에 추가해주는 방향으로
        String token = userService.login(requestDto, response);
        return responseEntity.setResponseEntity(null, ResponseMessage.LOGIN_OK, token, HttpStatus.OK);
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

    // private <T> ResponseEntity<DefaultResponseDto<T>> setResponseEntity(T data, String msg, HttpStatus status) {
    //     DefaultResponseDto<T> defaultResponse = new DefaultResponseDto<>(status.value(), msg, data);
    //     ResponseEntity<DefaultResponseDto<T>> ret = new ResponseEntity<>(defaultResponse, status);
    //     return ret;
    // }
    
    // private <T> ResponseEntity<DefaultResponseDto<T>> setResponseEntity(T data, String msg, String token, HttpStatus status) {
    //     DefaultResponseDto<T> defaultResponse = new DefaultResponseDto<>(status.value(), msg, data);
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.add(HttpHeaders.AUTHORIZATION, token);
    //     ResponseEntity<DefaultResponseDto<T>> ret = new ResponseEntity<>(defaultResponse, headers, status);
    //     return ret;
    // }

    class ResponseMessage {
        public static final String SIGNUP_OK = "회원가입 성공";
        public static final String LOGIN_OK = "로그인 성공";
    }
}
