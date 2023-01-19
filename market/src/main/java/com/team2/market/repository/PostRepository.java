package com.team2.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAllById(Long userId);


}
