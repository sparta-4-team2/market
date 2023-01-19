package com.team2.market.service;

import java.util.List;


import com.team2.market.dto.auth.response.*;
import com.team2.market.util.security.CustomUserDetails;

public interface AuthServiceInterface {
    RequestAuthResponseDto requestAuthorization(CustomUserDetails userDetails);
    List<RequestAuthResponseDto> getAllRequset();
    AuthChangeResponseDto changeAuthorization(Long requestId);
    
    AuthGetBuyerResponseDto getBuyerInfo(Long userId);
    List<AuthGetBuyerResponseDto> getAllBuyers();
    
    AuthGetSellerResponseDto getSellerInfo(Long sellerId);
    List<AuthGetSellerResponseDto> getAllSellers();
}
