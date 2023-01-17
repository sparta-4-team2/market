package com.team2.market.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void getOrdersForSeller() {

    }

    @PostMapping("/seller/orders/{orderid}")
    public String /*주문요청처리*/postMethodName(@RequestBody SomeEnityData entity,
                                 @PathVariable Long orderid,
                                 HttpServletRequest request) {
        //TODO: process POST request
        
        return "";
    }

    
    
}
