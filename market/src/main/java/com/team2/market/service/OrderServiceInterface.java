package com.team2.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;

import com.team2.market.dto.orders.request.*;
import com.team2.market.dto.orders.response.*;

public interface OrderServiceInterface {

    OrderResponseDto orderPost(OrderRequestDto requestDto, Long postid, UserDetails userDetails);
    List<OrderGetResponseDto> getAllOrders(OrderGetRequestDto requestDto, HttpServletRequest request);
    
}
