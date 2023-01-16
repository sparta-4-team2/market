package com.team2.market.service;

import java.util.List;

import com.team2.market.dto.product.request.ProductCreateRequestDto;
import com.team2.market.dto.product.request.ProductDeleteRequestDto;
import com.team2.market.dto.product.request.ProductGetRequestDto;
import com.team2.market.dto.product.request.ProductOrderRequestDto;
import com.team2.market.dto.product.request.ProductUpdateRequestDto;
import com.team2.market.dto.product.response.ProductCreateResponseDto;
import com.team2.market.dto.product.response.ProductDeleteResponseDto;
import com.team2.market.dto.product.response.ProductGetResponseDto;
import com.team2.market.dto.product.response.ProductUpdateResponseDto;

public interface ProductServiceInterface {
    ProductCreateResponseDto createProduct(ProductCreateRequestDto requestDto);
    ProductGetResponseDto getProduct(ProductGetRequestDto requestDto);
    List<ProductGetRequestDto> getAllProduct(ProductGetRequestDto requestDto);
    ProductUpdateResponseDto updateProduct(ProductUpdateRequestDto requestDto);
    ProductDeleteResponseDto deleteProduct(ProductDeleteRequestDto requestDto);
    void orderProduct(ProductOrderRequestDto requestDto);
}
