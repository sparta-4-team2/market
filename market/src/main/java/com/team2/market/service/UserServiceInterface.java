package com.team2.market.service;

import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;

public interface UserServiceInterface {
    SignupResponseDto createUser(SignupRequestDto requestDto);
    LoginResponseDto login(LoginRequestDto requestDto);
}
