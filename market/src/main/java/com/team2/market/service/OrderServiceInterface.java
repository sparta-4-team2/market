package com.team2.market.service;

import org.springframework.data.domain.Page;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.entity.User;

public interface OrderServiceInterface {

    Page<OrderResponseDto> getAllOrders(User user, int page, int type);
}
