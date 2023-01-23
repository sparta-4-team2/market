package com.team2.market.dto.auth.response;

import com.team2.market.entity.User;
import com.team2.market.entity.types.UserRoleType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetBuyerInfoResponseDto {

    public String nickname;
    public UserRoleType role;

    public GetBuyerInfoResponseDto(User user) {
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
