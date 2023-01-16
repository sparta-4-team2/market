package com.team2.market.service;

import com.team2.market.dto.users.request.*;
import com.team2.market.dto.users.response.*;

public interface UserServiceInterface {
    public SignupResponseDto createUser(SignupRequestDto requestDto);
    public LoginResponseDto login(LoginRequestDto requestDto);
}
