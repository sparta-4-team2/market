package com.team2.market.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.orders.request.*;
import com.team2.market.dto.orders.response.*;
import com.team2.market.service.OrderService;
import com.team2.market.util.statics.DefaultResponseEntity;

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

    @PostMapping("/posts/{postId}/orders")
    public ResponseEntity<Map<String, Object>> orderPost(@RequestBody OrderRequestDto requestDto,
                                      @PathVariable Long postId,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        
        OrderResponseDto responseDto = orderService.orderPost(requestDto, postId, userDetails);
        
        return DefaultResponseEntity.setResponseEntity(responseDto, ResponseMessage.ORDER_OK, HttpStatus.OK);
    }
    
    class ResponseMessage {
        public static final String ORDER_OK = "주문 요청 성공";
    }
}
