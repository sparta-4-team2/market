package com.team2.market.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.dto.types.SaleStatus;
import com.team2.market.entity.Post;


public interface PostRepository extends JpaRepository<Post, Long>{
	// List<Post> findAllBySellerIdAndStatus(Long sellerId, SaleStatus status, Pageable pageable);


}
