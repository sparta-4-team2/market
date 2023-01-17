package com.team2.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.team2.market.dto.orders.request.OrderGetRequestDto;
import com.team2.market.dto.orders.request.OrderRequestDto;
import com.team2.market.dto.orders.response.OrderGetResponseDto;
import com.team2.market.dto.orders.response.OrderResponseDto;

public interface OrderServiceInterface {

    OrderResponseDto orderPost(OrderRequestDto requestDto, Long postid, HttpServletRequest request);
    List<OrderGetResponseDto> getAllOrders(OrderGetRequestDto requestDto, HttpServletRequest request);
    
}
