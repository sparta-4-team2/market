package com.team2.market.dto.users.response;

import java.util.List;

import com.team2.market.entity.User;

import lombok.Getter;

@Getter
public class ProfileGetResponseDto <T>{
	private final String nickName;
	private final String profileImg;
	private final List<T> progress;
	private final List<T> success;

	public ProfileGetResponseDto(User user, List<T> progress, List<T> success) {
		this.nickName = user.getUsername();
		this.profileImg = user.getProfileImg();
		this.progress = progress;
		this.success = success;
	}
}
