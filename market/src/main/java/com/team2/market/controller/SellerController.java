package com.team2.market.controller;

import static com.team2.market.controller.SellerController.ResponseMessage.*;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.post.response.SellerPostForm;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.service.OrderService;
import com.team2.market.service.SellerService;
import com.team2.market.util.statics.DefaultResponseEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/sellers")
@RestController
public class SellerController {
	private final SellerService sellerService;
	private final OrderService orderService;

	@GetMapping("/profile")
	public ResponseEntity<ProfileGetResponseDto<SellerPostForm>> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto<SellerPostForm> profileDto = sellerService.getProfile(
			userDetails.getUsername());
		return ResponseEntity.ok(profileDto);
	}

	@PostMapping("/profile")
	public ResponseEntity<?> updateProfile(ProfileUpdateRequestDto request, @AuthenticationPrincipal UserDetails userDetails) {
		ProfileGetResponseDto<SellerPostForm> profileDto = sellerService.updateProfile(
			request, userDetails.getUsername());
		return ResponseEntity.ok(profileDto);
	}

	@GetMapping("/orders") // 판매자가 자신에게 온 거래신청 목록 조회
	public ResponseEntity<Map<String, Object>> getOrdersForSeller(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam("p") int page) {
		List<OrderResponseDto> posts = sellerService.getAllOrders(userDetails.getUsername(),
			page);
		return DefaultResponseEntity.setResponseEntity(posts, POST_LIST_OK, HttpStatus.OK);
	}

	@PostMapping("/orders/{orderId}")
	public String processOrderRequest(@PathVariable Long orderId,
		@AuthenticationPrincipal UserDetails userDetails) {
		orderService.processOrderRequest(orderId, userDetails.getUsername());

		return "";
	}

	class ResponseMessage {
		public static final String POST_LIST_OK = "거래 신청 이력 조회 성공";
	}
}
