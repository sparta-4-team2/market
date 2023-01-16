package com.team2.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
