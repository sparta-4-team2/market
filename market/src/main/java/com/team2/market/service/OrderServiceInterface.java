package com.team2.market.service;

import javax.servlet.http.HttpServletRequest;

import com.team2.market.dto.product.request.PostOrderRequestDto;
import com.team2.market.dto.product.response.PostOrderResponseDto;

public interface OrderServiceInterface {

    PostOrderResponseDto orderPost(PostOrderRequestDto requestDto, Long postid, HttpServletRequest request);
}
