package com.team2.market.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.team2.market.dto.product.request.PostCreateRequestDto;
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

    public Post(String productName, int price, String contents) {
        this.productName = productName;
        this.price = price;
        this.contents = contents;
    }
    public Post(PostCreateRequestDto requestDto, Long id) {
        this.title = requestDto.getTitle();
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();;
        this.contents = requestDto.getContents();;

    }

}
