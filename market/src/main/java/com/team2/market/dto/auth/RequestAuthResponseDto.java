package com.team2.market.dto.auth;

import com.team2.market.entity.AuthRequest;
import com.team2.market.entity.types.RequestType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAuthResponseDto {
    private Long userId;
    private RequestType type;

    public RequestAuthResponseDto(AuthRequest request) {
        this.userId = request.getUser().getId();
        this.type = request.getType();
    }

}
