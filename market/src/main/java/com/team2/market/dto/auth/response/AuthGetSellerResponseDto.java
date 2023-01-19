package com.team2.market.dto.auth.response;

import java.util.List;

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
    private List<Post> postlist;

    public AuthGetSellerResponseDto(Seller seller) {
        this.userId = seller.getUser().getId();
        this.nickname = seller.getUser().getNickname();
        this.sellerId = seller.getId();
        this.postlist = seller.getPosts();
    }
}
