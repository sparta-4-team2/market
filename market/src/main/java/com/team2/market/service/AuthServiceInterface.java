package com.team2.market.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.team2.market.dto.auth.response.*;

public interface AuthServiceInterface {
    RequestAuthResponseDto requestAuthorization(UserDetails userDetails);
    AuthChangeResponseDto changeAuthorization(Long requestId);
    
    List<AuthGetBuyerResponseDto> getAllBuyers();
    AuthGetBuyerResponseDto getUserInfo();
    
    List<AuthGetSellerResponseDto> getAllSellers();
    AuthGetSellerResponseDto getSellerInfo();
}
