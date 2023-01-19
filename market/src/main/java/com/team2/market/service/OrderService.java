package com.team2.market.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.post.response.SellerPostForm;
import com.team2.market.dto.types.OrderResultType;
import com.team2.market.dto.types.SaleResultType;
import com.team2.market.entity.Order;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.repository.OrderRepository;
import com.team2.market.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService implements OrderServiceInterface {

    private final PostRepository postRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final SellerService Sellerservice;


    @Override
    // Post Url에서 접근
    public OrderResponseDto orderPost(Long postId, String username) {

        // 구매 유저 정보
        User user = userService.findByUsername(username);
        
        // 구매하려는 상품 게시글 정보
        Post post = null;
            // postService.getPost(postId);
        
        // 방어코드. 실질적으로는 동작할일 없는 코드긴하지만, url 변조가 의심되는 상황에서는 해당 처리가 가능.
        if(post == null) {
            throw new IllegalArgumentException("해당되는 포스트가 존재하지 않습니다.");
        }

        Order order = new Order(post, user);
        orderRepository.save(order);
        
        return new OrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders(String username, int page) {
        User user = userService.findByUsername(username);

        PageRequest sortByTime = PageRequest.of(page, 5, Sort.by("tradeStartTime"));
        List<Order> orders = orderRepository.findAllByUserAndOrderType(user,
            OrderResultType.IN_PROGRESS, sortByTime);

        return OrderResponseDto.from(orders);
    }

    public List<SellerPostForm> getAllOrders2(String username, int page) {
        Seller seller = Sellerservice.findByUsername(username);

        PageRequest sortByTime = PageRequest.of(page, 5,
            Sort.by(Sort.Direction.DESC, "tradeStartTime"));

        List<Post> posts = postRepository.findAllBySellerIdAndForSale(
            seller.getId(), SaleResultType.F, sortByTime);

        return SellerPostForm.from(posts);
    }

    public void processOrderRequest(Long orderId, String username) {
        Order order = orderRepository.findById(orderId)
            .orElse(null);

        assert (order != null) : "order is null";

        if (!order.getPost().getSeller().getUser().getUsername().equals(username)) {
            System.out.println("Authorization Error");
            return;
        }

        // order 주문상태 변경 -> 주문성공
        order.successOrder();
    }

    public List<OrderResponseDto> getOrderResponseDtoList(String tradeEndTime, User user, OrderResultType orderResultType) {
        PageRequest pageable = getPageRequest(tradeEndTime);
        List<Order> orders = orderRepository.findAllByUserAndOrderType(user, orderResultType, pageable);
        return OrderResponseDto.from(orders);
    }

    @NotNull
    private PageRequest getPageRequest(String property) {
        return PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC,property));
    }

}
