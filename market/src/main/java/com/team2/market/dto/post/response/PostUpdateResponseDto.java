package com.team2.market.dto.post.response;

import com.team2.market.entity.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
