package com.team2.market.entity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import com.team2.market.dto.post.request.*;

import com.team2.market.dto.types.PostStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column (nullable = false) //컬럼 값이 null 반환 X
    private String productName;

    @Column (nullable = false)
    private int price;

    @Column(nullable = false)
    private String contents;

    // @ElementCollection
    // @CollectionTable(
    //     name = "ordertopost",
    //     joinColumns = @JoinColumn(name = "post_id")
    // )
    // @MapKeyColumn(name ="user_id")
    // @Column
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column
    private Map<Long, Order> orderToPost = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column
    @Enumerated(value = EnumType.STRING)
    private PostStatus status; // 판매 진행 or 완료 여부

    private final OffsetDateTime tradeStartTime = OffsetDateTime.now();

    private OffsetDateTime tradeEndTime = OffsetDateTime.of(1, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

    public Post(PostCreateRequestDto requestDto, Seller seller) {
        this.title = requestDto.getTitle();
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.contents = requestDto.getContents();
        
        this.seller = seller;
        this.status = PostStatus.STILL;
    }

    public void updatePost(PostUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.contents = requestDto.getContents();

    }

    public void putOrderMap(Long userId, Order value) {
        orderToPost.put(userId, value);
    }

    public boolean isFinish() {
        return this.status.equals(PostStatus.FINISH);
    }

    public void updateStatus(PostStatus status) {
        this.status = status;
    }

    public void finishSale() {
        this.status = PostStatus.FINISH;
    }

}
