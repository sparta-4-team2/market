package com.team2.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.market.dto.product.request.ProductCreateRequestDto;
import com.team2.market.dto.product.request.ProductDeleteRequestDto;
import com.team2.market.dto.product.request.ProductGetRequestDto;
import com.team2.market.dto.product.request.ProductUpdateRequestDto;
import com.team2.market.dto.product.response.ProductCreateResponseDto;
import com.team2.market.dto.product.response.ProductDeleteResponseDto;
import com.team2.market.dto.product.response.ProductGetResponseDto;
import com.team2.market.dto.product.response.ProductUpdateResponseDto;
import com.team2.market.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInterface{

    private final ProductRepository productRepository;

    @Override
    public ProductCreateResponseDto createProduct(ProductCreateRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductGetResponseDto getProduct(ProductGetRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductGetRequestDto> getAllProduct(ProductGetRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductUpdateResponseDto updateProduct(ProductUpdateRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductDeleteResponseDto deleteProduct(ProductDeleteRequestDto requestDto) {
        // TODO Auto-generated method stub
        return null;
    }

}
