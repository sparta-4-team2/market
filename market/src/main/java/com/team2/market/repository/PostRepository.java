package com.team2.market.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.entity.Post;
import com.team2.market.type.SaleResultType;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findAllBySellerIdAndForSale(Long sellerId, SaleResultType type, Pageable pageable);

	List<Post> findAllById(Long userId);

    Optional<Post> findAllById(Long id, Long userId);
}
