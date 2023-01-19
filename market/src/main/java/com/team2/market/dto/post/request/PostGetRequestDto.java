package com.team2.market.dto.post.request;

import com.team2.market.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostGetRequestDto {
    //게시글 정보를 리턴할 응답 클래스

    private String title;
    private String productName;
    private int price;
    private String contents;




}
