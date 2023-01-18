package com.team2.market.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findAllBySellerIdAndForSale(Long sellerId, boolean postStatus, Pageable pageable);
}
