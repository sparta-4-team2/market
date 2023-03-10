package com.team2.market.dto.post.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.team2.market.dto.types.PostStatus;
import com.team2.market.entity.Post;

import lombok.Getter;

@Getter
public class SellerPostForm {
	private final String productName;
	private final int price;
	private final OffsetDateTime tradeTime;
	private final PostStatus forSale;

	public SellerPostForm(Post post) {
		this.productName = post.getProductName();
		this.price = post.getPrice();
		this.forSale = post.getStatus();
		this.tradeTime = this.forSale.equals(PostStatus.FINISH) ? post.getTradeStartTime() :
				post.getTradeEndTime();
	}

	public static List<SellerPostForm> from (List<Post> posts) {
		return posts.stream()
			.map(SellerPostForm::new)
			.collect(Collectors.toList());
	}
}
