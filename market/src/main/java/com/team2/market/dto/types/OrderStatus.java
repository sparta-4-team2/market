package com.team2.market.dto.types;

import java.util.Arrays;

public enum OrderStatus {
	ALL(0),
	IN_PROGRESS(1),
	SUCCESS(2);

	private int type;

	OrderStatus(int type) {
		this.type = type;
	}

	public static OrderStatus typeToOrderStatus(int type) {
		return Arrays.stream(values())
					.filter(value -> value.type == type)
					.findFirst()
					.orElse(null);
	}
}
