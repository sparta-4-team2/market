package com.team2.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    @Override
    // Post Url에서 접근
    public OrderResponseDto orderPost(OrderRequestDto requestDto, Long postid, UserDetails userDetails) {

        // 구매 유저 정보
        User user = getUser(userDetails);;
        
        // 구매하려는 상품 게시글 정보
        Post post = getPost(postid);
        
        // 방어코드. 실질적으로는 동작할일 없는 코드긴하지만, url 변조가 의심되는 상황에서는 해당 처리가 가능.
        if(post == null){
            throw new IllegalArgumentException("해당되는 포스트가 존재하지 않습니다.");
        }

        Order order = new Order(post, user);
        orderRepository.save(order);
        
        return new OrderResponseDto(order);
    }


    private User getUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("cannot found user"));
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
