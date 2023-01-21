package com.team2.market.dto.auth.response;

import com.team2.market.entity.AuthRequest;
import com.team2.market.entity.types.RequestStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAuthResponseDto {
    private Long requestId;
    private Long userId;
    private RequestStatus type;

    public RequestAuthResponseDto(AuthRequest request) {
        this.requestId = request.getId();
        this.userId = request.getUser().getId();
        this.type = request.getStatus();
    }

}
