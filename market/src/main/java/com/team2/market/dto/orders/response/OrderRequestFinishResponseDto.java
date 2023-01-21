package com.team2.market.dto.orders.response;

import com.team2.market.dto.types.OrderStatus;
import com.team2.market.entity.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestFinishResponseDto {

    private Long orderId;
    private Long postId;
    private OrderStatus status;
    private Long customerId;
    private Long sellerId;

    public OrderRequestFinishResponseDto(Order order) {
        this.orderId = order.getId();
        this.postId = order.getPost().getId();
        this.status = order.getStatus();
        this.customerId = order.getUser().getId();
        this.sellerId = order.getPost().getSeller().getId();
    }

}
