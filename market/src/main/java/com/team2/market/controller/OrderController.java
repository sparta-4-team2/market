package com.team2.market.controller;

import static com.team2.market.controller.OrderController.ResponseMessage.*;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.orders.response.*;
import com.team2.market.dto.post.response.SellerPostForm;
import com.team2.market.service.OrderService;
import com.team2.market.util.statics.DefaultResponseEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@GetMapping("/orders") // 사용자가 자신의 주문내역 조회
	public ResponseEntity<Map<String, Object>> getOrdersForCustomer(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam("p") int page) {
		List<OrderResponseDto> orders = orderService.getAllOrders(userDetails.getUsername(), page);

		return DefaultResponseEntity.setResponseEntity(orders, ORDER_LIST_OK, HttpStatus.OK);
	}

	@GetMapping("/seller/orders") // 판매자가 자신의 판매내역 조회
	public ResponseEntity<Map<String, Object>> getOrdersForSeller(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam("p") int page) {
		List<SellerPostForm> posts = orderService.getAllOrders2(userDetails.getUsername(),
			page);
		return DefaultResponseEntity.setResponseEntity(posts, POST_LIST_OK, HttpStatus.OK);
	}

	@PostMapping("/seller/orders/{orderId}")
	public String /*주문요청처리*/postMethodName(@PathVariable Long orderId,
		@AuthenticationPrincipal UserDetails userDetails) {
		orderService.processOrderRequest(orderId, userDetails.getUsername());

		return "";
	}

	@PostMapping("/posts/{postId}/request")
	public ResponseEntity<Map<String, Object>> orderPost(@PathVariable Long postId,
		@AuthenticationPrincipal UserDetails userDetails) {

		OrderResponseDto responseDto = orderService.orderPost(postId, userDetails.getUsername());

		return DefaultResponseEntity.setResponseEntity(
			responseDto, ResponseMessage.ORDER_OK, HttpStatus.OK);
	}

	class ResponseMessage {
		public static final String ORDER_OK = "주문 요청 성공";
		public static final String ORDER_LIST_OK = "주문 이력 조회 성공";
		public static final String POST_LIST_OK = "주문 이력 조회 성공";
	}
}
