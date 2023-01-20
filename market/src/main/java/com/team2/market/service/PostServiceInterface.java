package com.team2.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.team2.market.dto.post.request.*;
import com.team2.market.dto.post.response.*;
import com.team2.market.util.security.CustomUserDetails;

public interface PostServiceInterface {
    PostCreateResponseDto createPost(PostCreateRequestDto requestDto, CustomUserDetails userDetails);
    PostGetResponseDto getPost(PostGetRequestDto requestDto, Long postid, CustomUserDetails userDetails);
    List<PostGetResponseDto> getAllPost(PostGetRequestDto requestDto, CustomUserDetails userDetails);
    PostUpdateResponseDto updatePost(PostUpdateRequestDto requestDto, Long postid, CustomUserDetails userDetails);
    PostDeleteResponseDto deletePost(PostDeleteRequestDto requestDto, Long postid, CustomUserDetails userDetails);
}
