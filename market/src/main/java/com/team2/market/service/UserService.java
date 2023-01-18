package com.team2.market.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.dto.orders.response.UserOrderForm;
import com.team2.market.dto.users.request.LoginRequestDto;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.request.SignupRequestDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.entity.Order;
import com.team2.market.entity.User;
import com.team2.market.entity.UserRoleEnum;
import com.team2.market.repository.OrderRepository;
import com.team2.market.repository.UserRepository;
import com.team2.market.type.OrderResultType;
import com.team2.market.util.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JwtUtil jwtService;

    @Override
    public void createUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        UserRoleEnum role = UserRoleEnum.BUYER;
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    @Override
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("등록된 사용자가 없습니다."));

        if (!user.isValidPassword(requestDto.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtService.createToken(user.getUsername(), user.getRole()));
    }
    @Transactional
    @Override
    public ProfileGetResponseDto updateProfile(ProfileUpdateRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "세부사항 후에 추가"));
        user.updateProfile(requestDto);

        PageRequest page = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC,"tradeStartTime"));
        List<Order> inProgressOrders = orderRepository.findAllByUserAndOrderType(user, OrderResultType.IN_PROGRESS,page);
        List<Order> successOrders = orderRepository.findAllByUserAndOrderType(user,
            OrderResultType.SUCCESS, page);

        List<UserOrderForm> progress = inProgressOrders.stream()
            .map(UserOrderForm::new)
            .collect(Collectors.toList());
        List<UserOrderForm> success = successOrders.stream()
            .map(UserOrderForm::new)
            .collect(Collectors.toList());

        return new ProfileGetResponseDto(userRepository.save(user), progress, success);
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileGetResponseDto getProfile(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "세부사항 후에 추가"));

        PageRequest page = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC,"tradeStartTime"));
        List<Order> inProgressOrders = orderRepository.findAllByUserAndOrderType(user, OrderResultType.IN_PROGRESS,page);
        List<Order> successOrders = orderRepository.findAllByUserAndOrderType(user,
            OrderResultType.SUCCESS, page);

        List<UserOrderForm> progress = inProgressOrders.stream()
            .map(UserOrderForm::new)
            .collect(Collectors.toList());
        List<UserOrderForm> success = successOrders.stream()
            .map(UserOrderForm::new)
            .collect(Collectors.toList());

        return new ProfileGetResponseDto(user, progress, success);
    }


}
