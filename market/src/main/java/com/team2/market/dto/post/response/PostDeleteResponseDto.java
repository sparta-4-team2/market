package com.team2.market.dto.post.response;

import com.team2.market.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDeleteResponseDto {
    private Long id;
    public PostDeleteResponseDto(Post post) {
        this.id = getId();
    }
}
