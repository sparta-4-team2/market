package com.team2.market.dto.users.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private boolean isAdmin = false;
    private String adminToken = "";
}
