package com.team2.market.dto.users.response;

import com.team2.market.dto.users.request.SignupRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupResponseDto {

    private final String RESPONSE_MSG = "회원가입 성공";

    public SignupResponseDto (SignupRequestDto requestDto) {

    }
}
