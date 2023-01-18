package com.team2.market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.market.dto.post.response.SellerPostForm;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.service.SellerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class SellerController {
	private final SellerService sellerService;

	@GetMapping("/seller/profile")
	public ResponseEntity<ProfileGetResponseDto<SellerPostForm>> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto<SellerPostForm> profileDto = sellerService.getProfile(
			userDetails.getUsername());
		return ResponseEntity.ok(profileDto);
	}

	@PostMapping("/seller/profile")
	public ResponseEntity<?> updateProfile(ProfileUpdateRequestDto request, @AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto<SellerPostForm> profileDto = sellerService.updateProfile(
			request, userDetails.getUsername());
		return ResponseEntity.ok(profileDto);
	}
}
