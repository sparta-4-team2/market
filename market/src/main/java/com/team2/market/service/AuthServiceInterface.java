package com.team2.market.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.team2.market.dto.auth.response.*;

public interface AuthServiceInterface {
    RequestAuthResponseDto requestAuthorization(UserDetails userDetails);
    List<RequestAuthResponseDto> getAllRequset();
    AuthChangeResponseDto changeAuthorization(Long requestId);
    
    AuthGetBuyerResponseDto getBuyerInfo(Long userId);
    List<AuthGetBuyerResponseDto> getAllBuyers();
    
    AuthGetSellerResponseDto getSellerInfo(Long sellerId);
    List<AuthGetSellerResponseDto> getAllSellers();
}
