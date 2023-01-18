package com.team2.market.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.team2.market.dto.post.response.SellerPostForm;
import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.dto.users.response.ProfileGetResponseDto;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;
import com.team2.market.repository.PostRepository;
import com.team2.market.repository.SellerRepository;
import com.team2.market.type.SaleResultType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SellerService {
	private final PostRepository postRepository;
	private final SellerRepository sellerRepository;

	public ProfileGetResponseDto<SellerPostForm> updateProfile(ProfileUpdateRequestDto request, String username) {
		Seller seller = findByUsername(username);
		seller.getUser().updateProfile(request);

		return getSellerPostFormProfileGetResponseDto(seller);
	}

	public ProfileGetResponseDto<SellerPostForm> getProfile(String username) {
		Seller seller = findByUsername(username);

		return getSellerPostFormProfileGetResponseDto(seller);
	}

	private Seller findByUsername(String username) {
		return sellerRepository.findByUserName(username)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 필요"));
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
			seller, SaleResultType.F, page);

		PageRequest page2 = getPageRequest("tradeEndTime");
		List<SellerPostForm> success = getSellerPostForms(
			seller, SaleResultType.S, page2);
		return new ProfileGetResponseDto<>(seller.getUser(), progress, success);
	}

	@NotNull
	private List<SellerPostForm> getSellerPostForms(Seller seller, SaleResultType type,
		PageRequest page) {
		List<Post> posts = postRepository.findAllBySellerIdAndForSale(
			seller.getId(), type, page);
		return SellerPostForm.from(posts);
	}
}
