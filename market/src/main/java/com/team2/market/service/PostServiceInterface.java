package com.team2.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.team2.market.dto.post.request.*;
import com.team2.market.dto.post.response.*;
import com.team2.market.entity.Post;

public interface PostServiceInterface {
    PostCreateResponseDto createPost(PostCreateRequestDto requestDto, HttpServletRequest request);
    PostGetResponseDto getPost(PostGetRequestDto requestDto, Long postid, HttpServletRequest request);
    List<Post> getAllPost(PostGetRequestDto requestDto, HttpServletRequest request);
    PostUpdateResponseDto updatePost(PostUpdateRequestDto requestDto, Long postid, HttpServletRequest request);
    PostDeleteResponseDto deletePost(PostDeleteRequestDto requestDto, Long postid, HttpServletRequest request);
}
