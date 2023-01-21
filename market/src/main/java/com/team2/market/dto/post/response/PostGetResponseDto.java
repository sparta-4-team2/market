package com.team2.market.dto.post.response;

import com.team2.market.dto.types.SaleStatus;
import com.team2.market.entity.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostGetResponseDto {
    // 상품 게시글 번호
    private Long id;
    // 상품 게시글 제목
    private String title;
    // 상품 게시글 내용
    private String contents;
    // 상품 이름
    private String productName;
    // 상품 가격
    private int price;
    private SaleStatus type;
    // 판매자
    private Long sellerId;
    private String sellerName;

    public PostGetResponseDto(Post post) {
        // 게시글
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();

        // 판매 상품 및 가격
        this.productName = post.getProductName();
        this.price = post.getPrice();
        this.type = post.getStatus();

        // 판매자
        this.sellerId = post.getSeller().getId();
        this.sellerName = post.getSeller().getUser().getUsername();
    }
}
