package com.team2.market.service;

import org.springframework.data.domain.Page;

import com.team2.market.dto.auth.response.*;
import com.team2.market.entity.User;

public interface AuthServiceInterface {
    RequestAuthResponseDto requestAuthorization(User user);
    Page<RequestAuthResponseDto> getAllRequset(int page);
    AuthChangeResponseDto changeAuthorization(Long requestId);
    
    GetBuyerInfoResponseDto getBuyerInfo(Long userId);
    Page<GetBuyerInfoResponseDto> getAllBuyers(int page);
    
    GetSellerInfoResponseDto getSellerInfo(Long sellerId);
    Page<GetSellerInfoResponseDto> getAllSellers(int page);
}
