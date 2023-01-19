package com.team2.market.service;

import java.util.List;

import com.team2.market.dto.orders.request.*;
import com.team2.market.dto.orders.response.*;

public interface OrderServiceInterface {

    OrderResponseDto orderPost(Long postId, String username);

    List<OrderResponseDto> getAllOrders(String username, int page);
}
