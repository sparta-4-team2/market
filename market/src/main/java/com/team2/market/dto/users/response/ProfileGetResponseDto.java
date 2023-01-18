package com.team2.market.dto.users.response;

import java.util.List;

import com.team2.market.dto.orders.response.UserOrderForm;
import com.team2.market.entity.User;

import lombok.Getter;

@Getter
public class ProfileGetResponseDto {
	private final String nickName;
	private final String profileImg;

	private final List<UserOrderForm> progress;
	private final List<UserOrderForm> success;

	public ProfileGetResponseDto(User user, List<UserOrderForm> progress, List<UserOrderForm> success) {
		this.nickName = user.getUsername();
		this.profileImg = user.getProfileImg();
		this.progress = progress;
		this.success = success;
	}
}
