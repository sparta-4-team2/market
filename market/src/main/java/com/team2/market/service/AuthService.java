package com.team2.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.market.dto.auth.response.AuthChangeResponseDto;
import com.team2.market.dto.auth.response.RequestAuthResponseDto;
import com.team2.market.entity.AuthRequest;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.entity.types.RequestType;
import com.team2.market.entity.types.UserRoleType;
import com.team2.market.repository.AuthRequestRepository;
import com.team2.market.repository.SellerRepository;
import com.team2.market.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {
    private final UserRepository userRepository;
    private final AuthRequestRepository authRequestRepository;
    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public AuthChangeResponseDto changeAuthorization(Long requestId) {
        // 요청 번호를 통해 해당 요청 확인
        AuthRequest request = getRequest(requestId);
        if(request == null)
            throw new IllegalArgumentException("해당 요청이 존재하지 않습니다.");
        
        // 요청을 통해 유저의 권한 변경
        User user = request.getUser();
        user.updateRole(UserRoleType.SELLER);

        // Seller를 DB목록에 추가
        Seller seller = new Seller(user);
        sellerRepository.save(seller);
        
        // 데이터 반환
        AuthChangeResponseDto responseDto = new AuthChangeResponseDto(user);
        
        return responseDto;
    }

    private AuthRequest getRequest(Long requestId) {
        return authRequestRepository.findById(requestId).orElse(null);
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
        List<AuthRequest> requests = authRequestRepository.findAll();
        return requests.stream().map(RequestAuthResponseDto::new).collect(Collectors.toList());
    }

}
