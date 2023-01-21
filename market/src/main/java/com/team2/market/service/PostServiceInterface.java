package com.team2.market.service;

import java.util.List;

import com.team2.market.dto.post.request.*;
import com.team2.market.dto.post.response.*;
import com.team2.market.entity.User;
import com.team2.market.util.security.CustomUserDetails;

public interface PostServiceInterface {
    PostCreateResponseDto createPost(PostCreateRequestDto requestDto, User user);
    PostGetResponseDto getPost(Long postid, CustomUserDetails userDetails);
    List<PostGetResponseDto> getAllPost(CustomUserDetails userDetails);
    PostUpdateResponseDto updatePost(PostUpdateRequestDto requestDto, Long postid, User user);
    PostDeleteResponseDto deletePost(Long postid, CustomUserDetails userDetails);
}
