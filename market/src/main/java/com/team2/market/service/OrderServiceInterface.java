package com.team2.market.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.entity.Post;
import com.team2.market.entity.User;

public interface OrderServiceInterface {
    OrderResponseDto sendOrderToSeller(User user, Post post);

    Page<OrderResponseDto> getAllOrders(User user, int page, int type);
}
