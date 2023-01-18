package com.team2.market.dto.product.response;

import com.team2.market.entity.Order;
import com.team2.market.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor//lombok 사용해서 필요는 X
public class PostCreateResponseDto {
    //등록된 상품 name, price, contents, productid
    private Long id;
    private String title;
    private String productName;
    private int price;
    private long product_id; //post에는 없음(erd에만...)
    private String contents;
    private List<Order> orderlist;
    public PostCreateResponseDto(String title, String productName, int price, long product_id, String contents) {
        this.title = title;
        this.productName = productName;
        this.price = price;
        this.product_id = product_id;
        this.contents = contents;
    }

    public PostCreateResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.productName = post.getProductName();
        this.price = post.getPrice();
        this.contents = post.getContents();
        this.orderlist = post.getOrderlist();
    }
}
