package com.team2.market.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.orders.request.*;
import com.team2.market.dto.orders.response.*;
import com.team2.market.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public void getOrdersForCustomer() {

    }

    @GetMapping("/seller/orders")
    public List<OrderGetResponseDto> getOrdersForSeller() {
        return orderService.getAllOrders(null, null);
    }

    @PostMapping("/seller/orders/{orderid}")
    public String /*주문요청처리*/postMethodName(@RequestBody String entity,
                                 @PathVariable Long orderid,
                                 HttpServletRequest request) {
        //TODO: process POST request
        
        return "";
    }

    @PostMapping("/posts/{postid}/request")
    public OrderResponseDto orderPost(@RequestBody OrderRequestDto requestDto,
                                          @PathVariable Long postid,
                                          HttpServletRequest request) {
        
        return orderService.orderPost(requestDto, postid, request);
    }
    
}
