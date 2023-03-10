package com.team2.market.service;

import org.springframework.data.domain.Page;

import com.team2.market.dto.post.request.*;
import com.team2.market.dto.post.response.*;
import com.team2.market.entity.User;
import com.team2.market.util.security.CustomUserDetails;

public interface PostServiceInterface {
    PostCreateResponseDto createPost(PostCreateRequestDto requestDto, User user);
    PostGetResponseDto getPost(Long postid, CustomUserDetails userDetails);
    Page<PostGetResponseDto> getAllPost(User user, int page, int type);
    PostUpdateResponseDto updatePost(PostUpdateRequestDto requestDto, Long postid, User user);
    PostDeleteResponseDto deletePost(Long postid, CustomUserDetails userDetails);
}
