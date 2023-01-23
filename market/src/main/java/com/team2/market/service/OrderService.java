package com.team2.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.dto.orders.response.OrderRequestFinishResponseDto;
import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.types.OrderStatus;
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
    public Page<OrderResponseDto> getAllOrders(User user, int page, int type) {
        OrderStatus status = OrderStatus.typeToOrderStatus(type);

        PageRequest sortByTime = PageRequest.of(page, 5, Sort.by("tradeStartTime"));
        Page<Order> orders;
        if(status.equals(OrderStatus.ALL)) {
            orders = orderRepository.findAllByUser(user, sortByTime);
        }
        else {
            orders = orderRepository.findAllByUserAndStatus(user, status, sortByTime);
        }

        return orders.map(OrderResponseDto::new);
    }

    @Transactional
    public OrderRequestFinishResponseDto processOrderRequest(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "error"));

        if (!order.getPost().getSeller().getUser().getUsername().equals(user.getUsername())) { // method 화
            throw new IllegalArgumentException("Authorization Error"); // 인증 오류 예외처리
        }

        // order 주문상태 변경 -> 주문성공
        order.successOrder();
        // post 판매 종료 처리
        order.getPost().finishSale();

        return new OrderRequestFinishResponseDto(order);
    }

    public List<OrderResponseDto> getOrderResponseDtoList(String tradeEndTime, User user, OrderStatus orderResultType) {
        PageRequest pageable = getPageRequest(tradeEndTime);
        List<Order> orders = orderRepository.findAllByUserAndStatus(user, orderResultType, pageable).stream().collect(Collectors.toList());
        return OrderResponseDto.from(orders);
    }

    @NotNull
    private PageRequest getPageRequest(String property) {
        return PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC,property));
    }

    public Page<OrderResponseDto> getAllOrdersForSeller(Seller seller, int page, int type) {
        OrderStatus status = OrderStatus.typeToOrderStatus(type);
        PageRequest tradeStartTime = getPageRequest("tradeStartTime");
        Page<Order> orders;

        if(status.equals(OrderStatus.ALL)) {
            orders = orderRepository.findAllBySeller(seller, tradeStartTime);
        } else {
            orders = orderRepository.findAllBySellerAndStatus(seller, status, tradeStartTime);
        }
        
        return orders.map(OrderResponseDto::new);
    }

    /** 주문 Entity 저장 Repository 지원 메소드
     * @param user 구매를 원하는 유저
     * @param post 구매를 원하는 품목
     * @return 구매를 성공했을 경우, 주문 요청 정보를 반환해준다.
     * @exception FinishedPostException 해당 주문 요청이, 판매 종료된 글에 보내졌을경우 판매 종료를 알리는 예외처리
     */
    @Transactional
    public Order saveOrder(User user, Post post) {
        return orderRepository.save(new Order(post, user));
    }
}
