package com.team2.market.dto.auth.response;

import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.entity.types.UserRoleType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthChangeResponseDto {
    private Long userId;
    private UserRoleType role;
    private Long sellerId;

    public AuthChangeResponseDto(Seller seller) {
        this.userId = seller.getUser().getId();
        this.role = seller.getUser().getRole();
        this.sellerId = seller.getId();
    }
}
