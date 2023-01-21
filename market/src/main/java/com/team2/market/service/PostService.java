package com.team2.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.market.dto.post.request.*;
import com.team2.market.dto.post.response.*;
import com.team2.market.dto.types.PostStatus;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.repository.PostRepository;
import com.team2.market.util.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface {

    private final PostRepository postRepository;
    private final SellerService sellerService;

    @Transactional//상품 게시글 등록
    public PostCreateResponseDto createPost(PostCreateRequestDto requestDto, User user) {

        Seller seller = sellerService.findByUsername(user.getUsername());

        Post post = postRepository.save(new Post(requestDto, seller));
        seller.addPost(post);

        return new PostCreateResponseDto(post);
    }

    @Transactional(readOnly = true)//관심 상품 조회
    @Override
    public PostGetResponseDto getPost(Long postid, CustomUserDetails userDetails) {
        Post post = getPost(postid);

        return new PostGetResponseDto(post);
    }

    @Transactional
    public Post getPost(Long postid) {
        return postRepository.findById(postid).orElseThrow( 
            () -> new IllegalArgumentException("조회할 수 있는 게시글이 없습니다.")
        );
    }

    //전체 상품 조회
    @Transactional(readOnly = true)
    @Override
    public List<PostGetResponseDto> getAllPost(CustomUserDetails userDetails) {
        List<Post> posts = getAllPost();
        return posts.stream().map(PostGetResponseDto::new).collect(Collectors.toList());
    }


    private List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Transactional  //게시글 수정
    @Override
    public PostUpdateResponseDto updatePost(PostUpdateRequestDto requestDto, Long postId,
                                            User user) {
        Post post = getPost(postId);

        if(!isAuthority(user, post)) {
                throw new IllegalArgumentException("글 수정 권한이 없습니다.");
        }
        
        post.updatePost(requestDto);
        
        return new PostUpdateResponseDto(post);
    }

    @Transactional //게시글 삭제
    @Override
    public PostDeleteResponseDto deletePost(Long postid,
                                            CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Post post = getPost(postid);

        if(!isAuthority(user, post)) {
             throw new IllegalArgumentException("글 삭제 권한이 없습니다.");
        }

        postRepository.delete(post);

        return new PostDeleteResponseDto(post);
    }

    private boolean isAuthority(User user, Post post) {
        return user.isAdmin() || post.getSeller().getUserId().equals(user.getId());
    }

    public void updatePostStatus(Post post, PostStatus status) {
        post.updateStatus(status);
    }
}