package com.team2.market.entity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.team2.market.dto.post.request.PostCreateRequestDto;
import com.team2.market.dto.types.SaleResultType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column (nullable = false) //컬럼 값이 null 반환 X
    private String productName;

    @Column (nullable = false)
    private int price;

    @Column(nullable = false)
    private String contents;

    @OneToMany(mappedBy = "post")
    private List<Order> orderlist = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private SaleResultType forSale; // 판매 진행 or 완료 여부

    private final OffsetDateTime tradeStartTime = OffsetDateTime.now();

    private OffsetDateTime tradeEndTime = OffsetDateTime.of(1, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

    public Post(String productName, int price, String contents) {
        this.productName = productName;
        this.price = price;
        this.contents = contents;
    }
    public Post(PostCreateRequestDto requestDto, Long id) {
        this.title = requestDto.getTitle();
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.contents = requestDto.getContents();

    }

}
