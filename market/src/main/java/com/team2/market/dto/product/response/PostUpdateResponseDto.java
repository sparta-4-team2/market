package com.team2.market.dto.product.response;

import com.team2.market.entity.Post;


public class PostUpdateResponseDto {
    private String title;
    private String contents;
    private String productName;
    private int price;
    private String nickname;

    public PostUpdateResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.productName = post.getProductName();
        this.price = post.getPrice();

    }
}


