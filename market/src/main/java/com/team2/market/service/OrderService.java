package com.team2.market.service;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.ServerEndpoint;

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
    private final JwtUtil jwtUtil;


    @Override
    public OrderResponseDto orderPost(OrderRequestDto requestDto, Long postid, HttpServletRequest request) {
        
        // servlet request에서 사용자의 인증 인가 확인
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("tokken error.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("Invalid user.")
            );
            
            // Order 하려면 해당 Post를 조회
            Post post = getPost(postid);
            
            if(post == null){
                return null;
            }

            Order order = new Order(post, user);
            
            orderRepository.save(order);
            
        }

        return null;
    }


    private Post getPost(Long postid) {
        return null;
    }


    @Override
    public OrderGetResponseDto getAllOrders(OrderGetRequestDto requestDto, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
