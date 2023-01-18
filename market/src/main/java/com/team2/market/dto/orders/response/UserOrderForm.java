package com.team2.market.dto.orders.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.team2.market.dto.types.OrderResultType;
import com.team2.market.entity.Order;

import lombok.Getter;

@Getter
public class UserOrderForm {
	private final String productName;
	private final int price;
	private final OffsetDateTime tradeTime;
	private final OrderResultType orderResultType;

	public UserOrderForm(Order order) {
		this.productName = order.getPost().getProductName();
		this.price = order.getPost().getPrice();
		this.orderResultType = order.getOrderType();
		this.tradeTime =
			orderResultType.equals(OrderResultType.IN_PROGRESS) ? order.getTradeStartTime() :
				order.getTradeEndTime();
	}

	public static List<UserOrderForm> from(List<Order> orders) {
		return orders.stream()
			.map(UserOrderForm::new)
			.collect(Collectors.toList());
	}
}
