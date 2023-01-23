package com.team2.market.dto.auth.response;

import java.util.List;
import java.util.stream.Collectors;

import com.team2.market.dto.post.response.PostGetResponseDto;
import com.team2.market.entity.Seller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetSellerInfoResponseDto {

    private Long userId;
    private String nickname;
    private Long sellerId; 
    private List<PostGetResponseDto> postlist;

    public GetSellerInfoResponseDto(Seller seller) {
        this.userId = seller.getUser().getId();
        this.nickname = seller.getUser().getNickname();
        this.sellerId = seller.getId();
        this.postlist = seller.getPostToSeller()
                                .values()
                                .stream()
                                .map(PostGetResponseDto::new)
                                .collect(Collectors.toList());
    }
}
