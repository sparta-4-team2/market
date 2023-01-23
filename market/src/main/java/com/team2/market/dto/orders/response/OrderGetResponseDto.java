package com.team2.market.dto.orders.response;

import com.team2.market.entity.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderGetResponseDto {
    private Long orderId;
    private Long buyerId;
    private Long sellerId;

    public OrderGetResponseDto(Order order) {
       this.orderId = order.getId();
       this.buyerId = order.getUser().getId();
       this.sellerId = order.getPost().getSeller().getId();
    }
}
