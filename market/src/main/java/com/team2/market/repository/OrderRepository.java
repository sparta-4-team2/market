package com.team2.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
