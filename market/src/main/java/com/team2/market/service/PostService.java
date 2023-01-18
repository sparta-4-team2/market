package com.team2.market.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.team2.market.dto.product.request.*;
import com.team2.market.dto.product.response.*;
import com.team2.market.entity.Post;
import com.team2.market.entity.User;
import com.team2.market.repository.OrderRepository;
import com.team2.market.repository.PostRepository;
import com.team2.market.repository.UserRepository;
import com.team2.market.util.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;

    @Transactional//상품 게시글 등록
    public PostCreateResponseDto createPost(PostCreateRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Post post = postRepository.saveAndFlush(new Post(requestDto, user.getId()));

            return new PostCreateResponseDto(post);
        } else {
            return null;
        }
    }

    @Override
    public List<PostGetRequestDto> getPost(PostGetRequestDto requestDto, Long postid, HttpServletRequest request) {
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

}
