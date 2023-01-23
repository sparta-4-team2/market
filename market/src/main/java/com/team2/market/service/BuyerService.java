package com.team2.market.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.entity.Order;
import com.team2.market.entity.Post;
import com.team2.market.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyerService {
    private final PostService postService;
    private final OrderService orderService;

    @Transactional
    public OrderResponseDto sendOrderToSeller(User user, Long postId) {
        Post post = postService.getPost(postId);
        
        if(post.isFinish()) throw new IllegalArgumentException("판매가 종료된 게시물입니다."); // finishedPostRequestException

        if(post.getOrderToPost().get(user.getId()) != null) {
            throw new IllegalArgumentException("중복된 요청 입니다."); // duplicateOrderRequestException
        }
        
        Order order = orderService.saveOrder(user, post);
        post.putOrderMap(user.getId(), order);

        return new OrderResponseDto(order);
    }
}