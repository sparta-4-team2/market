package com.team2.market.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.dto.types.PostStatus;
import com.team2.market.entity.Post;


public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findAllBySellerIdAndStatus(Long sellerId, PostStatus status, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByStatus(Pageable pageable, PostStatus status);


}
