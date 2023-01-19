package com.team2.market.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.market.dto.post.request.*;
import com.team2.market.dto.post.response.*;
import com.team2.market.entity.Post;
import com.team2.market.entity.User;
import com.team2.market.entity.types.UserRoleType;
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

    @Transactional(readOnly = true)//관심 상품 조회
    public PostGetResponseDto getPost(PostGetRequestDto requestDto, Long postid, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 조회 가능
        if (token != null) {
            // Token 검증
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

            // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
            UserRoleType userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<PostGetResponseDto> list = new ArrayList<>();
            List<Post> postList;

            if (userRoleEnum == UserRoleType.SELLER) {
                // 사용자 권한이 BUYER가 아닐 경우(SELLER, ADMIN일 경우)
                postList = new ArrayList<>()/* postRepository.findAllById(user.getId()) */;
            } else if (userRoleEnum == UserRoleType.ADMIN) {
                postList = new ArrayList<>() /*  postRepository.findAllById(user.getId()) */;
            // 로그인한 user들만 사용할 수 있지만 방어적으로 다 구현하자...(BUYER, SELLER, ADMIN)
            if (userRoleEnum == UserRoleEnum.BUYER) {
                // 사용자 권한이 USER 인 경우.
                postList = postRepository.findAllById(user.getId());
            } else if (userRoleEnum == UserRoleEnum.SELLER) {
                postList = postRepository.findAllById(user.getId());
            } else if (userRoleEnum == UserRoleEnum.ADMIN) {
                postList = postRepository.findAllById(user.getId());
            } else {
                throw new IllegalArgumentException("권한이 없습니다.");

            }
            for (Post post : postList) {
                list.add(new PostGetRequestDto(post));
            }

            Post post = postRepository.saveAndFlush(new Post(requestDto, user.getId()));
            return new PostGetResponseDto(post);

        } else {
            return null;
        }
    }

    @Transactional//전체 상품 조회
    public List<Post> getAllPost(PostGetRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 상품 조회 가능
        if (token != null) {
            // Token 검증
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

            // 사용자 권한 가져와서 ADMIN,SELLER 이면 전체 조회, BUYER 면 권한 없음
            UserRoleType userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<PostGetRequestDto> list = new ArrayList<>();
            List<Post> postList;

            if (userRoleEnum == UserRoleType.SELLER) {
                // 사용자 권한이 BUYER가 아닐 경우(SELLER, ADMIN일 경우)
                postList = postRepository.findAll();
            } else if (userRoleEnum == UserRoleType.ADMIN) {
                postList = postRepository.findAll();
            } else {
                throw new IllegalArgumentException("권한이 없습니다.");

            }
            for (Post post : postList) {
                list.add(new PostGetRequestDto(post));
            }

            return postList;

        } else {
            return null;
        }
    }



    @Transactional  //게시글 수정
    public PostUpdateResponseDto updatePost( PostUpdateRequestDto requestDto, Long postid,
                                  HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO로 DB에 저장할 객체 만들기
            Post post = postRepository.save(new Post(requestDto, user.getId()));

            return new PostUpdateResponseDto(post);
        } else {
            return null;
        }
    }

    @Override//삭제하기
    public PostDeleteResponseDto deletePost(PostDeleteRequestDto requestDto, Long postid, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
