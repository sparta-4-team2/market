package com.team2.market.dto.post.response;

import java.time.OffsetDateTime;

import com.team2.market.entity.Post;

import lombok.Getter;

@Getter
public class SellerPostForm {
	private final String productName;
	private final int price;
	private final OffsetDateTime tradeTime;
	private final boolean forSale;

	public SellerPostForm(Post post) {
		this.productName = post.getProductName();
		this.price = post.getPrice();
		this.forSale = post.isForSale();
		this.tradeTime = this.forSale ? post.getTradeStartTime() :
				post.getTradeEndTime();
	}
}
