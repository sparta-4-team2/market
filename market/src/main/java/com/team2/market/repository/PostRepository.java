package com.team2.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
    
}
