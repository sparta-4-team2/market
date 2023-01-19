package com.team2.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.types.OrderResultType;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {
	private final OrderService orderService;

	public ProfileGetResponseDto<OrderResponseDto> getUserOrderFormProfileGetResponseDto(User user) {
		List<OrderResponseDto> progress = orderService.getOrderResponseDtoList("tradeStartTime", user,
			OrderResultType.FAIL);

		List<OrderResponseDto> success = orderService.getOrderResponseDtoList("tradeEndTime", user,
			OrderResultType.SUCCESS);

		return new ProfileGetResponseDto<>(user, progress, success);
	}

}
