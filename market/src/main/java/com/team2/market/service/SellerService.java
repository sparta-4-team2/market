package com.team2.market.service;

import java.util.List;
import java.util.stream.Collectors;

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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SellerService {
	private final PostRepository postRepository;
	private final SellerRepository sellerRepository;

	public ProfileGetResponseDto<SellerPostForm> updateProfile(ProfileUpdateRequestDto request, String username) {
		Seller seller = sellerRepository.findByUserName(username)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 필요"));

		seller.getUser().updateProfile(request);

		return getSellerPostFormProfileGetResponseDto(seller);
	}

	public ProfileGetResponseDto<SellerPostForm> getProfile(String username) {
		Seller seller = sellerRepository.findByUserName(username)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 필요"));

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
		List<Post> progressPost = postRepository.findAllBySellerIdAndForSale(
			seller.getId(), true, page);
		List<SellerPostForm> progress = progressPost.stream()
			.map(SellerPostForm::new)
			.collect(Collectors.toList());

		PageRequest page2 = getPageRequest("tradeEndTime");
		List<Post> successPost = postRepository.findAllBySellerIdAndForSale(
			seller.getId(), false, page2);
		List<SellerPostForm> success = successPost.stream()
			.map(SellerPostForm::new)
			.collect(Collectors.toList());
		return new ProfileGetResponseDto<>(seller.getUser(), progress, success);
	}
}
