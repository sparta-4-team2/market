package com.team2.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.entity.AuthRequest;

public interface AuthRequestRepository extends JpaRepository<AuthRequest, Long>{

}
