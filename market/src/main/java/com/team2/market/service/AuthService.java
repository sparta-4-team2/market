package com.team2.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.market.dto.auth.response.AuthChangeResponseDto;
import com.team2.market.dto.auth.response.AuthGetBuyerResponseDto;
import com.team2.market.dto.auth.response.AuthGetSellerResponseDto;
import com.team2.market.dto.auth.response.RequestAuthResponseDto;
import com.team2.market.entity.AuthRequest;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.entity.types.RequestType;
import com.team2.market.entity.types.UserRoleType;
import com.team2.market.repository.AuthRequestRepository;
import com.team2.market.util.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {

    private final UserService userService;
    private final SellerService sellerService;

    private final AuthRequestRepository authRequestRepository;

    @Override
    @Transactional
    public AuthChangeResponseDto changeAuthorization(Long requestId) {
        // 요청 번호를 통해 해당 요청 확인
        AuthRequest request = getRequest(requestId);
        if(request == null)
            throw new IllegalArgumentException("해당 요청이 존재하지 않습니다.");
        
        // 요청을 통해 유저의 권한 변경
        User user = request.getUser();
        user.updateRole(UserRoleType.ROLE_SELLER);

        // Seller를 DB목록에 추가
        Seller seller = sellerService.save(new Seller(user));
        
        // 데이터 반환
        AuthChangeResponseDto responseDto = new AuthChangeResponseDto(seller);
        
        return responseDto;
    }

    // 유저 정보 프로필 참조 할듯?
    @Override
    @Transactional(readOnly = true)
    public AuthGetBuyerResponseDto getBuyerInfo(Long userId) {
        User user = userService.findById(userId);
        if (user.getRole() != UserRoleType.ROLE_BUYER)
            throw new IllegalArgumentException("고객이 아닙니다.");

        return new AuthGetBuyerResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthGetBuyerResponseDto> getAllBuyers() {
        List<User> userlist = userService.findAllByRole(UserRoleType.ROLE_BUYER);
        return userlist.stream().map(AuthGetBuyerResponseDto::new).collect(Collectors.toList());
    }
    
    // 판매자 정보 프로필 참조 할듯?
    @Override
    @Transactional(readOnly = true)
    public AuthGetSellerResponseDto getSellerInfo(Long sellerId) {
        Seller seller = sellerService.findById(sellerId);

        return new AuthGetSellerResponseDto(seller);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthGetSellerResponseDto> getAllSellers() {
        List<Seller> sellers = sellerService.findAll();
        return sellers.stream().map(AuthGetSellerResponseDto::new).collect(Collectors.toList());
    }


    /*
     * 판매자 등록 요청을 위한 service 메소드
     */
    @Override
    @Transactional
    public RequestAuthResponseDto requestAuthorization(CustomUserDetails userDetails) {
        // 유저의 존재 유무 확인
        User user = userDetails.getUser();

        // 예외 처리 해야함
        if(user.getRole() != UserRoleType.ROLE_BUYER){
            throw new IllegalArgumentException("이미 판매자거나 운영자인 회원입니다.");
        }

        AuthRequest request = new AuthRequest(user, RequestType.INPROGRESS);
        authRequestRepository.save(request);

        return new RequestAuthResponseDto(request);
    }

    // 현재 요청 타입에 관한 내용에 따라 가져올 수 있게 해야함
    @Override
    @Transactional(readOnly = true)
    public List<RequestAuthResponseDto> getAllRequset() {
        List<AuthRequest> requests = authRequestRepository.findAll();
        return requests.stream().map(RequestAuthResponseDto::new).collect(Collectors.toList());
    }

    private AuthRequest getRequest(Long requestId) {
        return authRequestRepository.findById(requestId).orElse(null);
    }

}
