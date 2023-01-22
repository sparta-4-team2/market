package com.team2.market.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team2.market.dto.types.OrderStatus;
import com.team2.market.entity.Order;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Page<Order> findAllByUserAndStatus(User user, OrderStatus status, Pageable pageable);

	@Query("select o from orders o where o.post.seller=:seller and o.status=:status")
	Page<Order> findAllBySellerAndStatus(@Param("seller") Seller seller,
		@Param("status") OrderStatus status, Pageable pageable);

	Page<Order> findAllByUser(User user, Pageable pageable);

	@Query("select o from orders o where o.post.seller=:seller")
	Page<Order> findAllBySeller(@Param("seller")Seller seller, PageRequest tradeStartTime);
}
