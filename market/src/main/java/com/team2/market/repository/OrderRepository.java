package com.team2.market.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.market.dto.types.OrderResultType;
import com.team2.market.entity.Order;
import com.team2.market.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findAllByUserAndOrderType(User user, OrderResultType type, Pageable pageable);
}
