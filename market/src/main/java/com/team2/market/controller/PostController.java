package com.team2.market.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.team2.market.entity.Post;
import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.post.request.*;
import com.team2.market.dto.post.response.*;
import com.team2.market.service.OrderService;
import com.team2.market.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService productService;
    private final OrderService orderService;

    @PostMapping("/posts")
    public PostCreateResponseDto createPost(@RequestBody PostCreateRequestDto requestDto,
                                                  HttpServletRequest request)
    {
        return productService.createPost(requestDto, request);
    }

    
    @GetMapping("/posts/{postid}")
    public List<PostGetRequestDto> getPost(@RequestBody PostGetRequestDto requestDto,
                                           @PathVariable Long postid,
                                           HttpServletRequest request)
    {
        return productService.getPost(requestDto, postid, request);
    }

    
    @GetMapping("/posts")
    public List<Post> getAllPost(@RequestBody PostGetRequestDto requestDto,
                                 HttpServletRequest request)
    {
        return productService.getAllPost(requestDto, request);
    }

    
    @PutMapping("/posts/{postid}")
    public PostUpdateResponseDto updatePost(@RequestBody PostUpdateRequestDto requestDto,
                                            @PathVariable Long postid,
                                            HttpServletRequest request)
    {
        return productService.updatePost(requestDto, postid, request);
    }

    
    @DeleteMapping("/posts/{postid}")
    public PostDeleteResponseDto deletePost(@RequestBody PostDeleteRequestDto requestDto,
                                            @PathVariable Long postid,
                                            HttpServletRequest request)
    {
        return productService.deletePost(requestDto, postid, request);
    }

    
}
