package com.team2.market.service;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;

public interface UserServiceInterface {
    void createUser(SignupRequestDto requestDto);
    String login(LoginRequestDto requestDto);
    ProfileGetResponseDto<OrderResponseDto> updateProfile(ProfileUpdateRequestDto requestDto, String username);
    ProfileGetResponseDto<OrderResponseDto> getProfile(String username);
}
