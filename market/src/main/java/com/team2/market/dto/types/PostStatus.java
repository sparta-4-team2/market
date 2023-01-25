package com.team2.market.dto.types;

import java.util.Arrays;

public enum PostStatus {
	ALL(0),
	STILL(1),
	FINISH(2);

	private int type;

	PostStatus(int type) {
		this.type = type;
	}

	public static PostStatus typeToStatus(int type) {
		return Arrays.stream(values())
					.filter(value -> value.type == type)
					.findFirst()
					.orElse(null);
	}

}
