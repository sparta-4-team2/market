package com.team2.market.dto.product.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateRequestDto {
    private String title;
    private String productName;
    private int price;
    private String contents;
}
