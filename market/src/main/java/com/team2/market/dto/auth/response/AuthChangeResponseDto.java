package com.team2.market.dto.auth.response;

import com.team2.market.entity.User;
import com.team2.market.entity.types.UserRoleType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthChangeResponseDto {
    private Long userId;
    private UserRoleType role;

    public AuthChangeResponseDto(User user) {
        this.userId = user.getId();
        this.role = user.getRole();
    }
}
