package com.team2.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}