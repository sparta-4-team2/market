package com.team2.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.team2.market.dto.product.request.*;
import com.team2.market.dto.product.response.*;
import com.team2.market.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface{

    private final PostRepository productRepository;

    @Override
    public PostCreateResponseDto createPost(PostCreateRequestDto requestDto, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PostGetResponseDto getPost(PostGetRequestDto requestDto, Long postid, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PostGetResponseDto> getAllPost(PostGetRequestDto requestDto, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PostUpdateResponseDto updatePost(PostUpdateRequestDto requestDto, Long postid, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PostDeleteResponseDto deletePost(PostDeleteRequestDto requestDto, Long postid, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void orderProduct(PostOrderRequestDto requestDto, HttpServletRequest request) {
        // TODO Auto-generated method stub
        
    }

}
