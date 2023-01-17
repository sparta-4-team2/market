package com.team2.market.dto.product.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor//lombok 사용해서 필요는 X
public class PostCreateResponseDto {
    //등록된 상품 name, price, contents, productid
    private String title;
    private String productName;
    private int price;
    private long product_id; //post에는 없음(erd에만...)
    private String contents;
    public PostCreateResponseDto(String title, String productName, int price, long product_id, String contents) {
        this.title = title;
        this.productName = productName;
        this.price = price;
        this.product_id = product_id;
        this.contents = contents;
    }
}
