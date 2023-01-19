package com.team2.market.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.entity.Post;
import com.team2.market.entity.User;
import com.team2.market.entity.types.UserRoleType;
import com.team2.market.repository.UserRepository;
import com.team2.market.util.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;
    private final JwtUtil jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final OrderService orderService;
    private final PostService postService;

    @Override
    public void createUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        UserRoleType role = UserRoleType.BUYER;
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    @Override
    public String login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("등록된 사용자가 없습니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtService.createToken(user.getUsername(), user.getRole());
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileGetResponseDto<OrderResponseDto> getProfile(Long userId) {
        User user = findById(userId);
        return profileService.getUserOrderFormProfileGetResponseDto(user);
    }
    @Transactional
    @Override
    public ProfileGetResponseDto<OrderResponseDto> updateProfile(ProfileUpdateRequestDto requestDto, String username) {
        User user = findByUsername(username);
        user.updateProfile(requestDto);
        return profileService.getUserOrderFormProfileGetResponseDto(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "세부사항 후에 추가"));
    }

    /** 유저를 userId를 통해 조회
     * @param userId 사용자의 Id
     * @return User의 정보
     */
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    /** 유저를 Type에 따라 전체 조회
     *
     * @param role UserRoleType을 통해 조회
     * @return
     */
    public List<User> findAllByRole(UserRoleType role) {
        return userRepository.findAllByRole(role);
    }

    public List<OrderResponseDto> getAllOrders(String username, int page) {
        User user = findByUsername(username);
        return orderService.getAllOrders(user, page);
    }

    public OrderResponseDto sendOrderToSeller(String username, Long postId) {
        User user = findByUsername(username);
        Post post = null;
        //postService.getPost(postId)
        return orderService.sendOrderToSeller(user, post);
    }
}
