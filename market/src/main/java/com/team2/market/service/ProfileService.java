package com.team2.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.types.OrderStatus;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {
	private final OrderService orderService;

	public ProfileGetResponseDto<OrderResponseDto> getUserOrderFormProfileGetResponseDto(User user) {
		List<OrderResponseDto> progress = orderService.getOrderResponseDtoList("tradeStartTime", user,
			OrderStatus.ALL);

		List<OrderResponseDto> success = orderService.getOrderResponseDtoList("tradeEndTime", user,
			OrderStatus.SUCCESS);

		return new ProfileGetResponseDto<>(user, progress, success);
	}

}
