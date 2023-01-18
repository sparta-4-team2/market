package com.team2.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.team2.market.dto.orders.request.OrderGetRequestDto;
import com.team2.market.dto.orders.request.OrderRequestDto;
import com.team2.market.dto.orders.response.OrderGetResponseDto;
import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.entity.Order;
import com.team2.market.entity.Post;
import com.team2.market.entity.User;
import com.team2.market.repository.OrderRepository;
import com.team2.market.repository.PostRepository;
import com.team2.market.repository.UserRepository;
import com.team2.market.util.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    @Override
    // Post Url에서 접근
    public OrderResponseDto orderPost(OrderRequestDto requestDto, Long postid, String username) {

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("Invalid user.")
        );
        
        // Order 하려면 해당 Post를 조회
        Post post = getPost(postid);
        
        // 방어코드. 실질적으로는 동작할일 없는 코드긴하지만, url 변조가 의심되는 상황에서는 해당 처리가 가능.
        if(post == null){
            throw new IllegalArgumentException("해당되는 포스트가 존재하지 않습니다.");
        }

        Order order = new Order(post, user);
        orderRepository.save(order);
        
        return null;
    }


    private Post getPost(Long postid) {
        return postRepository.findById(postid).orElse(null);
    }


    @Override
    public List<OrderGetResponseDto> getAllOrders(OrderGetRequestDto requestDto, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
