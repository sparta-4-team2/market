package com.team2.market.util.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.post.request.PostCreateRequestDto;
import com.team2.market.dto.post.response.PostCreateResponseDto;
import com.team2.market.entity.Order;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.entity.types.UserRoleType;
import com.team2.market.service.AuthService;
import com.team2.market.service.BuyerService;
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
    private final BuyerService buyerService;
    private final SellerService sellerService;
    
    @Override
    @Transactional 
    public void run(ApplicationArguments args) throws Exception {
        createUnitData();
        // createLargeData();
    }

    private void createUnitData() {
        // 일반 유저 생성
        User testUser1 = createUser("User1", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        User testUser2 = createUser("User2", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        User testUser3 = createUser("User3", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        User testUser4 = createUser("User4", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        User testUser5 = createUser("User5", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        
        // 관리자 유저 생성
        User adminUser1 = userService.save(createUser("Admin1", "1q2w3e4r!", UserRoleType.ROLE_ADMIN));

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

        OrderResponseDto order1 = buyerService.sendOrderToSeller(testUser3, post1.getId());   
        OrderResponseDto order2 = buyerService.sendOrderToSeller(testUser4, post2.getId());   
        OrderResponseDto order3 = buyerService.sendOrderToSeller(testUser5, post3.getId());   
        OrderResponseDto order4 = buyerService.sendOrderToSeller(testUser3, post4.getId());   
        OrderResponseDto order5 = buyerService.sendOrderToSeller(testUser4, post5.getId());   
        OrderResponseDto order6 = buyerService.sendOrderToSeller(testUser5, post6.getId());   

        orderService.processOrderRequest(order1.getOrderId(), testUser1);
        orderService.processOrderRequest(order4.getOrderId(), testUser2);
        
    }

    private void createLargeData() {

        // 일반 유저 생성
        User testUser1 = createUser("User1", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        User testUser2 = createUser("User2", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        User testUser3 = createUser("User3", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        User testUser4 = createUser("User4", "1q2w3e4r!", UserRoleType.ROLE_BUYER);
        User adminUser1 = userService.save(createUser("Admin1", "1q2w3e4r!", UserRoleType.ROLE_ADMIN));
        

        // 관리자 유저 생성
        //testUser1, testUser2의 권한 변경 요청
        Long requestId1 = authService.requestAuthorization(testUser1).getRequestId();
        Long requestId2 = authService.requestAuthorization(testUser2).getRequestId();
        
        // 관리자 유저의 해당 값들 변경
        authService.changeAuthorization(requestId1);
        authService.changeAuthorization(requestId2);
        
        // 더미 데이터 유저 대량 생성
        List<User> users = autoUserGenerate(10000);
        // 더미 판매자 신청 대량 생성
        List<Long> requests = autoCreateRequest(users, 2000);
        List<Seller> sellers = autoAuthChange(requests, 300);

        Post post1 = createPost(new PostCreateRequestDto("제목1", "판매물1", 10000, "내용1"), testUser1);
        Post post2 = createPost(new PostCreateRequestDto("제목2", "판매물2", 10000, "내용2"), testUser1);
        Post post3 = createPost(new PostCreateRequestDto("제목3", "판매물3", 10000, "내용3"), testUser1);
        Post post4 = createPost(new PostCreateRequestDto("제목4", "판매물4", 10000, "내용4"), testUser2);
        Post post5 = createPost(new PostCreateRequestDto("제목5", "판매물5", 10000, "내용5"), testUser2);
        Post post6 = createPost(new PostCreateRequestDto("제목6", "판매물6", 10000, "내용6"), testUser2);

        List<Post> posts = autoPostGenerate(sellers, 20000);
        
        OrderResponseDto order1 = buyerService.sendOrderToSeller(testUser3, post1.getId());  
        OrderResponseDto order4 = buyerService.sendOrderToSeller(testUser3, post4.getId());   

        List<Order> orders = autoOrderRequestGenerate(users, posts, 5000);
        
        orderService.processOrderRequest(order1.getOrderId(), testUser1);
        orderService.processOrderRequest(order4.getOrderId(), testUser2);
    }

    private List<Order> autoOrderRequestGenerate(List<User> users, List<Post> posts, int size) {
        List<OrderResponseDto> orders = new ArrayList<>();
        Random random1 = new Random();
        Random random2 = new Random();
        for(int i=0; i<size; i++) 
        {
            buyerService.sendOrderToSeller(users.get(random1.nextInt(users.size())), posts.get(random2.nextInt(posts.size())).getId());
        }
        return orderService.findAll();
    }

    private List<Post> autoPostGenerate(List<Seller> sellers, int size) {
        List<Post> posts = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<size; i++)
        {
            String postTitle = "제목" + random.nextInt(100000);
            String productName = "판매 상품" + random.nextInt(100000);
            int price =  random.nextInt(100000) % 1000 * 1000;
            String contents = "내용" + random.nextInt(100000);
            posts.add( 
                    createPost(
                        new PostCreateRequestDto(postTitle, productName, price, contents), 
                        sellers.get(random.nextInt(sellers.size())).getUser()));
        }
        return postService.findAll();
    }

    private List<User> autoUserGenerate(int size) {
        List<User> users = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<size; i++) {
            String username = "User"+ String.valueOf(random.nextInt(100000));
            String password = "1q2w3e4r!";
            users.add(createUser(username, password, UserRoleType.ROLE_BUYER));
        }

        return userService.findAll();
    }

    private List<Long> autoCreateRequest(List<User> users, int size) {
        List<Long> requests = new ArrayList<>();
        for(int i=0; i<size; i++) {
            requests.add(authService.requestAuthorization(users.get(i+5)).getRequestId());
            if(requests.get(i) == null)
                continue;
        }
        return requests;
    }

    private List<Seller> autoAuthChange(List<Long> requests, int size) {
        for(int i=0; i<size; i++) {
            authService.changeAuthorization(requests.get(i));
        }
        return sellerService.findAll();
    }

    private User createUser(String username, String password, UserRoleType role) {
        return userService.save(new User(username, passwordEncoder.encode(password), role));
    }

    private Post createPost(PostCreateRequestDto requestDto, User user) {
        PostCreateResponseDto response = postService.createPost(requestDto, user);
        return postService.getPost(response.getId());
    }
}
