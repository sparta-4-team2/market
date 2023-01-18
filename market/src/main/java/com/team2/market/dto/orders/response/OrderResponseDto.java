package com.team2.market.dto.orders.response;

import com.team2.market.entity.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private Long postId;
    
    public OrderResponseDto(Order order){
        this.orderId = order.getId();
        this.userId = order.getUser().getId();
        this.postId = order.getPost().getId();
    }
    
}
