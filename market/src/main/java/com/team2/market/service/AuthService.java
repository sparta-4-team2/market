package com.team2.market.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.team2.market.dto.auth.response.RequestAuthResponseDto;
import com.team2.market.entity.AuthRequest;
import com.team2.market.entity.User;
import com.team2.market.entity.types.RequestType;
import com.team2.market.entity.types.UserRoleType;
import com.team2.market.repository.AuthRequestRepository;
import com.team2.market.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {
    private final UserRepository userRepository;
    private final AuthRequestRepository authRequestRepository;

    @Override
    public void setAuthorization() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void getCustomInfo() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void getSellerInfo() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void getAllSellers() {
        // TODO Auto-generated method stub
        
    }


    /*
     * 판매자 등록 요청을 위한 service 메소드
     */
    @Override
    public RequestAuthResponseDto requestAuthorization(UserDetails userDetails) {
        // 유저의 존재 유무 확인
        User user = getUser(userDetails);

        // 예외 처리 해야함
        if(user.getRole() != UserRoleType.BUYER){
            return null;
        }

        AuthRequest request = new AuthRequest(user, RequestType.SELLERAUTH);
        authRequestRepository.save(request);

        return new RequestAuthResponseDto(request);
    }

    private User getUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("cannot found user"));
    }

    public List<RequestAuthResponseDto> getAllRequset() {
        return null;
    }

}
