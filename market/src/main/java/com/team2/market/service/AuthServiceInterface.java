package com.team2.market.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.team2.market.dto.auth.*;
import com.team2.market.dto.auth.response.RequestAuthResponseDto;

public interface AuthServiceInterface {
    void setAuthorization();
    void getCustomInfo();
    void getSellerInfo();
    void getAllSellers();
    RequestAuthResponseDto requestAuthorization(UserDetails userDetails);
    
}
