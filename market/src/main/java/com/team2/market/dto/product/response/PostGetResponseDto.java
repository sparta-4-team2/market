package com.team2.market.dto.product.response;

import com.team2.market.entity.Post;

public class PostGetResponseDto {
    //작성자명, title, contents, product name, price, (날짜..)
    private String title;
    private String contents;
    private String productName;
    private int price;


    public PostGetResponseDto(Post post) {
        this.title = post.getTitle();
        this.productName = post.getProductName();
        this.price = post.getPrice();
        this.contents = post.getContents();
    }
}