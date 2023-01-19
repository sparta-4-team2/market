package com.team2.market.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.product.request.*;
import com.team2.market.dto.product.response.*;
import com.team2.market.service.OrderService;
import com.team2.market.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final OrderService orderService;

    @PostMapping("/posts")
    public PostCreateResponseDto createPost(@RequestBody PostCreateRequestDto requestDto,
                                                  HttpServletRequest request)
    {
        return postService.createPost(requestDto, request);
    }

    
    @GetMapping("/posts/{postid}")//관심상품 조회 -> 게시물 하나씩 선택해서 볼 수 있다. ->굳이 리스트로 만들 필요 X.
    public PostGetResponseDto getPost(@RequestBody PostGetRequestDto requestDto,
                                            @PathVariable Long postid,
                                            HttpServletRequest request)
    {
        return postService.getPost(requestDto, postid, request);
    }

    
    @GetMapping("/posts")
    public List<PostGetResponseDto> getAllPost(@RequestBody PostGetRequestDto requestDto,
                                               HttpServletRequest request)
    {
        return postService.getAllPost(requestDto, request);
    }

    
    @PutMapping("/posts/{postid}")
    public PostUpdateResponseDto updatePost(@RequestBody PostUpdateRequestDto requestDto,
                                            @PathVariable Long postid,
                                            HttpServletRequest request)
    {
        return postService.updatePost(requestDto, postid, request);
    }

    
    @DeleteMapping("/posts/{postid}")
    public PostDeleteResponseDto deletePost(@RequestBody PostDeleteRequestDto requestDto,
                                            @PathVariable Long postid,
                                            HttpServletRequest request)
    {
        return postService.deletePost(requestDto, postid, request);
    }

    
}
