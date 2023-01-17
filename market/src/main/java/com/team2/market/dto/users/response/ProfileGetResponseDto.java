package com.team2.market.dto.users.response;

import com.team2.market.entity.User;

import lombok.Getter;

@Getter
public class ProfileGetResponseDto {
	private final String nickName;
	private final String profileImg;

	public ProfileGetResponseDto(User user) {
		this.nickName = user.getUsername();
		this.profileImg = user.getProfileImg();
	}
}
