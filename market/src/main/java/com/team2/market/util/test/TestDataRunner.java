package com.team2.market.util.test;

import javax.transaction.Transactional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.post.request.PostCreateRequestDto;
import com.team2.market.dto.post.response.PostCreateResponseDto;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.entity.types.UserRoleType;
import com.team2.market.service.AuthService;
import com.team2.market.service.OrderService;
import com.team2.market.service.PostService;
import com.team2.market.service.SellerService;
import com.team2.market.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {
    
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthService authService;
    private final PostService postService;
    private final OrderService orderService;
    private final SellerService sellerService;
    
    @Override
    @Transactional 
    public void run(ApplicationArguments args) throws Exception {
        
        // 일반 유저 생성
        User testUser1 = userService.save(createUser("User1", "1q2w3e4r!", UserRoleType.ROLE_BUYER));
        User testUser2 = userService.save(createUser("User2", "1q2w3e4r!", UserRoleType.ROLE_BUYER));
        User testUser3 = userService.save(createUser("User3", "1q2w3e4r!", UserRoleType.ROLE_BUYER));
        User testUser4 = userService.save(createUser("User4", "1q2w3e4r!", UserRoleType.ROLE_BUYER));
        User testUser5 = userService.save(createUser("User5", "1q2w3e4r!", UserRoleType.ROLE_BUYER));

        // 관리자 유저 생성
        User adminUser1 = userService.save(createUser("Admin1", "1q2w3e4r!", UserRoleType.ROLE_BUYER));

        //testUser1, testUser2의 권한 변경 요청
        Long requestId1 = authService.requestAuthorization(testUser1).getRequestId();
        Long requestId2 = authService.requestAuthorization(testUser2).getRequestId();

        // 관리자 유저의 해당 값들 변경
        authService.changeAuthorization(requestId1);
        authService.changeAuthorization(requestId2);

        // 각각 유저들의 판매글 작성
        Post post1 = createPost(new PostCreateRequestDto("제목1", "판매물1", 10000, "내용1"), testUser1);
        Post post2 = createPost(new PostCreateRequestDto("제목2", "판매물2", 10000, "내용2"), testUser1);
        Post post3 = createPost(new PostCreateRequestDto("제목3", "판매물3", 10000, "내용3"), testUser1);
        Post post4 = createPost(new PostCreateRequestDto("제목4", "판매물4", 10000, "내용4"), testUser2);
        Post post5 = createPost(new PostCreateRequestDto("제목5", "판매물5", 10000, "내용5"), testUser2);
        Post post6 = createPost(new PostCreateRequestDto("제목6", "판매물6", 10000, "내용6"), testUser2);

        OrderResponseDto order1 = orderService.sendOrderToSeller(testUser3, post1);   
        OrderResponseDto order2 = orderService.sendOrderToSeller(testUser4, post2);   
        OrderResponseDto order3 = orderService.sendOrderToSeller(testUser5, post3);   
        OrderResponseDto order4 = orderService.sendOrderToSeller(testUser3, post4);   
        OrderResponseDto order5 = orderService.sendOrderToSeller(testUser4, post5);   
        OrderResponseDto order6 = orderService.sendOrderToSeller(testUser5, post6);   

        orderService.processOrderRequest(order1.getOrderId(), testUser1);
        orderService.processOrderRequest(order4.getOrderId(), testUser2);
        
    }

    private User createUser(String username, String password, UserRoleType role) {
        return new User(username, passwordEncoder.encode(password), role);
    }

    private Post createPost(PostCreateRequestDto requestDto, User user) {
        PostCreateResponseDto response = postService.createPost(requestDto, user);
        return postService.getPost(response.getId());
    }
}
