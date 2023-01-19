package com.team2.market.dto.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String contents;
    private String productName;
    private int price;
    private String nickname;
}
