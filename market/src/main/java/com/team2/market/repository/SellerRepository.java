package com.team2.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team2.market.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
	@Query("select s from Seller s where s.user.username=:username")
	Optional<Seller> findByUserName(@Param("username") String username);
}
