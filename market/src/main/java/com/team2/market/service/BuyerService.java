package com.team2.market.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.entity.Post;
import com.team2.market.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyerService {
    private final UserService userService;
    private final PostService postService;
    private final OrderService orderService;

    @Transactional
    public OrderResponseDto sendOrderToSeller(String username, Long postId) {
        User user = userService.findByUsername(username);
        Post post = postService.getPost(postId);
        
        return orderService.sendOrderToSeller(user, post);
    }
}
