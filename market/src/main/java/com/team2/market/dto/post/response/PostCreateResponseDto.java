package com.team2.market.dto.post.response;

import com.team2.market.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor//lombok 사용해서 필요는 X
public class PostCreateResponseDto {
    //등록된 상품 name, price, contents, productid
    private Long id;
    private String title;
    private String productName;
    private int price;
    private String contents;

    public PostCreateResponseDto(String title, String productName, int price, String contents) {
        this.title = title;
        this.productName = productName;
        this.price = price;
        this.contents = contents;
    }

    public PostCreateResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.productName = post.getProductName();
        this.price = post.getPrice();
        this.contents = post.getContents();
    }

}
