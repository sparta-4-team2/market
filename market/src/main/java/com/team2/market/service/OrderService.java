package com.team2.market.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.types.OrderStatus;
import com.team2.market.dto.types.PostStatus;
import com.team2.market.entity.Order;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;

    @Override
    public List<OrderResponseDto> getAllOrders(User user, int page) {

        PageRequest sortByTime = PageRequest.of(page, 5, Sort.by("tradeStartTime"));
        List<Order> orders = orderRepository.findAllByUserAndStatus(user,OrderStatus.IN_PROGRESS, sortByTime);

        return OrderResponseDto.from(orders);
    }

    public void processOrderRequest(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "error"));

        if (!order.getPost().getSeller().getUser().getUsername().equals(user.getUsername())) { // method 화
            System.out.println("Authorization Error");
            return;
        }

        // order 주문상태 변경 -> 주문성공
        order.successOrder();
    }

    public List<OrderResponseDto> getOrderResponseDtoList(String tradeEndTime, User user, OrderStatus orderResultType) {
        PageRequest pageable = getPageRequest(tradeEndTime);
        List<Order> orders = orderRepository.findAllByUserAndStatus(user, orderResultType, pageable);
        return OrderResponseDto.from(orders);
    }

    @NotNull
    private PageRequest getPageRequest(String property) {
        return PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC,property));
    }

    public List<OrderResponseDto> getAllOrdersForSeller(Seller seller, int page) {
        PageRequest tradeStartTime = getPageRequest("tradeStartTime");
        List<Order> orders = orderRepository.findAllBySellerAndStatus(seller, OrderStatus.IN_PROGRESS, tradeStartTime);
        
        return OrderResponseDto.from(orders);
    }

    /** 구매자의 판매글에 해당 상품 구매 요청
     * @param user 구매를 원하는 유저
     * @param post 구매를 원하는 품목
     * @return 구매를 성공했을 경우, 주문 요청 정보를 반환해준다.
     * @exception FinishedPostException 해당 주문 요청이, 판매 종료된 글에 보내졌을경우 판매 종료를 알리는 예외처리
     */
    @Transactional
    public OrderResponseDto sendOrderToSeller(User user, Post post) {
        
        Order order = new Order(post, user);
        orderRepository.save(order);
        post.addOrder(order);
        user.addOrder(order);
        return new OrderResponseDto(order);
    }
}
