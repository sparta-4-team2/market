package com.team2.market.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.team2.market.dto.auth.response.AuthChangeResponseDto;
import com.team2.market.dto.auth.response.GetBuyerInfoResponseDto;
import com.team2.market.dto.auth.response.GetSellerInfoResponseDto;
import com.team2.market.dto.auth.response.RequestAuthResponseDto;
import com.team2.market.service.AuthService;

import com.team2.market.util.security.CustomUserDetails;
import com.team2.market.util.statics.DefaultResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthController {
    private final AuthService authService;

    /** 구매자의 판매자 권한 요청
     *  user의 현재 권한이 Buyer일경우만 가능하게 할것 
     * @param userDetails
     */
    @PostMapping("/request")
    public ResponseEntity<Map<String, Object>> requestAuth(@AuthenticationPrincipal CustomUserDetails userDetails) {
        RequestAuthResponseDto data = authService.requestAuthorization(userDetails.getUser());

        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.AUTHREQUEST_OK, HttpStatus.OK);
    }

    /** 운영자의 구매자 권한 요청 목록 확인
     *  운영자 권한 넣어줘야할 것
     *  forbidden 처리? vs 컨트롤러 내부동작으로 처리?
     * @param user
     */
    @GetMapping("/admin/request") 
    public ResponseEntity<Map<String, Object>> getAllAuthRequest
        (@AuthenticationPrincipal CustomUserDetails userDetails,
         @RequestParam("page") int page) 
    {
        Page<RequestAuthResponseDto> data = authService.getAllRequset(page);

        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.GETREQUEST_OK, HttpStatus.OK);
    }

    // Post나 Put 메소드 뭘 써야할지 잘몰라서 일시적으로 Post로 작성
    // 운영자 권한 판단 넣어줄 것
    /**
     * 
     * @param requestId
     * @return
     */
    @PostMapping("/admin/request/{requestId}")
    public ResponseEntity<Map<String, Object>> changeAuth(@PathVariable Long requestId) {
        AuthChangeResponseDto data = authService.changeAuthorization(requestId);
        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.AUTHCHANGE_OK, HttpStatus.OK);
    }

    /**
     * 
     * @return
     */
    @GetMapping("/seller")
    public ResponseEntity<Map<String, Object>> getAllSellers
        (@RequestParam("page") int page) 
    {
        Page<GetSellerInfoResponseDto> data = authService.getAllSellers(page);

        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.AUTHCHANGE_OK, HttpStatus.OK);
    }

    /**
     * 
     * @param sellerId
     * @return
     */
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Map<String, Object>> getSellerInfo
        (@PathVariable Long sellerId) 
        {
        GetSellerInfoResponseDto data = authService.getSellerInfo(sellerId);

        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.AUTHCHANGE_OK, HttpStatus.OK);
    }

    /**
     * 
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getAllBuyers
        (@RequestParam("page") int page)
    {
        Page<GetBuyerInfoResponseDto> data = authService.getAllBuyers(page);

        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.GETALLBUYER_OK, HttpStatus.OK);
    }

    /**
     * 
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getBuyerInfo(@PathVariable Long userId) {
        GetBuyerInfoResponseDto data = authService.getBuyerInfo(userId);

        return DefaultResponseEntity.setResponseEntity(data, ResponseMessage.GETBUYERINFO_OK, HttpStatus.OK);
    }

    
    class ResponseMessage {
        public static final String AUTHREQUEST_OK = "판매자 권한 요청 성공";
        public static final String GETREQUEST_OK = "일반 고객의 권한 요청 목록 조회 성공";
        public static final String AUTHCHANGE_OK = "권한 변경 성공";
        public static final String GETALLBUYER_OK = "모든 유저 조회 성공";
        public static final String GETBUYERINFO_OK = "유저 정보 조회 성공";
    }
}
