package com.team2.market.dto.orders.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.team2.market.dto.types.OrderStatus;
import com.team2.market.entity.Order;

import lombok.Getter;

@Getter
public class OrderResponseDto {
	private final Long orderId;
	private final Long postId;
	private final String productName;
	private final int price;
	private final OffsetDateTime tradeTime;
	private final OrderStatus orderResultType;
	private final OrderStatus status;

	public OrderResponseDto(Order order) {
		this.orderId = order.getId();
		this.postId = order.getPost().getId();
		this.productName = order.getPost().getProductName();
		this.price = order.getPost().getPrice();
		this.orderResultType = order.getStatus();
		this.tradeTime =
			orderResultType.equals(OrderStatus.IN_PROGRESS) ? order.getTradeStartTime() :
				order.getTradeEndTime();
		this.status = order.getStatus();
	}

	public static List<OrderResponseDto> from(List<Order> orders) {
		return orders.stream()
			.map(OrderResponseDto::new)
			.collect(Collectors.toList());
	}
}
