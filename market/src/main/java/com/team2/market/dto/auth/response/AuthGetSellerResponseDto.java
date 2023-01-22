package com.team2.market.dto.auth.response;

import java.util.List;
import java.util.stream.Collectors;

import com.team2.market.dto.post.response.PostGetResponseDto;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthGetSellerResponseDto {

    private Long userId;
    private String nickname;
    private Long sellerId; 
    private List<PostGetResponseDto> postlist;

    public AuthGetSellerResponseDto(Seller seller) {
        this.userId = seller.getUser().getId();
        this.nickname = seller.getUser().getNickname();
        this.sellerId = seller.getId();
        this.postlist = seller.getPosts()
                                .stream()
                                .map(PostGetResponseDto::new)
                                .collect(Collectors.toList());
    }
}
