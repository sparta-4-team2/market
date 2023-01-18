package com.team2.market.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.dto.orders.response.UserOrderForm;
import com.team2.market.dto.types.OrderResultType;
import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.LoginResponseDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.entity.Order;
import com.team2.market.entity.User;
import com.team2.market.entity.types.UserRoleType;
import com.team2.market.repository.OrderRepository;
import com.team2.market.repository.UserRepository;
import com.team2.market.util.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;
    private final JwtUtil jwtService;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

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
    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("등록된 사용자가 없습니다."));

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtService.createToken(user.getUsername(), user.getRole());
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileGetResponseDto<UserOrderForm> getProfile(String username) {
        User user = findByUsername(username);

        return getUserOrderFormProfileGetResponseDto(user);
    }
    @Transactional
    @Override
    public ProfileGetResponseDto<UserOrderForm> updateProfile(ProfileUpdateRequestDto requestDto, String username) {
        User user = findByUsername(username);
        user.updateProfile(requestDto);

        return getUserOrderFormProfileGetResponseDto(user);
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "세부사항 후에 추가"));
    }

    @NotNull
    private ProfileGetResponseDto<UserOrderForm> getUserOrderFormProfileGetResponseDto(User user) {
        PageRequest pageSortByStartTime = getPageRequest("tradeStartTime");
        List<UserOrderForm> progress = getUserOrderForms(user, OrderResultType.IN_PROGRESS, pageSortByStartTime);

        PageRequest pageSortByEndTime = getPageRequest("tradeEndTime");
        List<UserOrderForm> success = getUserOrderForms(user, OrderResultType.SUCCESS,
            pageSortByEndTime);

        return new ProfileGetResponseDto<>(user, progress, success);
    }

    @NotNull
    private PageRequest getPageRequest(String property) {
        return PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC,property));
    }

    @NotNull
    private List<UserOrderForm> getUserOrderForms(User user, OrderResultType type,
        PageRequest pageSortByStartTime) {
        List<Order> orders = orderRepository.findAllByUserAndOrderType(user, type,
            pageSortByStartTime);
        return UserOrderForm.from(orders);
    }
}
