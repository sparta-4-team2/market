package com.team2.market.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.dto.orders.response.OrderResponseDto;
import com.team2.market.dto.post.response.SellerPostForm;
import com.team2.market.dto.types.PostStatus;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;
import com.team2.market.repository.PostRepository;
import com.team2.market.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SellerService {
	private final PostRepository postRepository;
	private final SellerRepository sellerRepository;
	private final OrderService orderService;

	public ProfileGetResponseDto<SellerPostForm> updateProfile(ProfileUpdateRequestDto request, User user) {
		Seller seller = findByUsername(user.getUsername());
		seller.getUser().updateProfile(request);

		return getSellerPostFormProfileGetResponseDto(seller);
	}

	public ProfileGetResponseDto<SellerPostForm> getProfile(User user) {
		Seller seller = findByUsername(user.getUsername());

		return getSellerPostFormProfileGetResponseDto(seller);
	}


	@NotNull
	private static PageRequest getPageRequest(String tradeStartTime) {
		return PageRequest.of(0, 5, Sort.by(tradeStartTime));
	}

	@NotNull
	private ProfileGetResponseDto<SellerPostForm> getSellerPostFormProfileGetResponseDto(
		Seller seller) {
		PageRequest page = getPageRequest("tradeStartTime");
		List<SellerPostForm> progress = getSellerPostForms(
			seller, PostStatus.FINISH, page);

		PageRequest page2 = getPageRequest("tradeEndTime");
		List<SellerPostForm> success = getSellerPostForms(
			seller, PostStatus.STILL, page2);
		return new ProfileGetResponseDto<>(seller.getUser(), progress, success);
	}

	@NotNull
	private List<SellerPostForm> getSellerPostForms(Seller seller, PostStatus type,
		PageRequest page) {
		List<Post> posts = postRepository.findAllBySellerIdAndStatus(seller.getId(), type, page);
		return SellerPostForm.from(posts);
	}

	public Page<OrderResponseDto> getAllOrders(User user, int page, int type) {
		Seller seller = findByUsername(user.getUsername());

		return orderService.getAllOrdersForSeller(seller, page, type);
	}

	/**
	 *
	 * @param seller
	 * @return
	 */
	public Seller save(Seller seller) {
		return sellerRepository.save(seller);
	}

	public Seller findById(Long sellerId) {
		return sellerRepository.findById(sellerId).orElseThrow( () -> new IllegalArgumentException("존재하지 않는 판매자입니다."));
	}

	public Page<Seller> findAll(Pageable pageable) {
		return sellerRepository.findAll(pageable);
	}
	
	public Seller findByUsername(String username) {
		return sellerRepository.findByUserName(username)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "판매자가 아닙니다."));
	}

	// 테스트 더미 데이터 지원 메소드
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }
}
