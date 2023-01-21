package com.team2.market.service;

import java.util.List;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.entity.Post;
import com.team2.market.entity.User;

public interface OrderServiceInterface {
    OrderResponseDto sendOrderToSeller(User user, Post post);

    List<OrderResponseDto> getAllOrders(User user, int page);
}
