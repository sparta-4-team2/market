package com.team2.market.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.post.request.*;
import com.team2.market.dto.post.response.*;
import com.team2.market.service.PostService;
import com.team2.market.util.security.CustomUserDetails;
import com.team2.market.util.statics.DefaultResponseEntity;

import lombok.RequiredArgsConstructor; 

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody PostCreateRequestDto requestDto,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostCreateResponseDto data =  postService.createPost(requestDto, userDetails.getUser());
        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.POSTCREATE_OK, HttpStatus.OK);
    }
    
    @GetMapping("/posts/{postid}")//관심상품 조회 -> 게시물 하나씩 선택해서 볼 수 있다. ->굳이 리스트로 만들 필요 X.
    public ResponseEntity<Map<String, Object>> getPost(@PathVariable Long postid,
                                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostGetResponseDto data = postService.getPost(postid, userDetails);
        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.POSTGET_OK, HttpStatus.OK);
    }
    
    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> getAllPost
        (@AuthenticationPrincipal CustomUserDetails userDetails,
         @RequestParam("page") int page,
         @RequestParam("type") int type) {
        Page<PostGetResponseDto> data = postService.getAllPost(userDetails.getUser(), page, type);
        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.POSTGETALL_OK, HttpStatus.OK);
    }
    
    @PutMapping("/posts/{postid}")
    public ResponseEntity<Map<String, Object>> updatePost(@RequestBody PostUpdateRequestDto requestDto,
                                                          @PathVariable Long postid,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostUpdateResponseDto data = postService.updatePost(requestDto, postid, userDetails.getUser());
        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.POSTUPDATE_OK, HttpStatus.OK);
    }
    
    @DeleteMapping("/posts/{postid}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable Long postid,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostDeleteResponseDto data =  postService.deletePost(postid, userDetails);
        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.POSTDELETE_OK, HttpStatus.OK);
    }

    class ResponseMessage {
        private static final String POSTCREATE_OK = "게시글 생성 성공";
        private static final String POSTGET_OK = "게시글 정보 조회 성공";
        private static final String POSTGETALL_OK = "게시글 목록 조회 성공";
        private static final String POSTUPDATE_OK = "게시글 수정 성공";
        private static final String POSTDELETE_OK = "게시글 삭제 성공";
    }
}
