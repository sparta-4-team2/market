package com.team2.market.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team2.market.dto.types.OrderResultType;
import com.team2.market.dto.types.SaleResultType;
import com.team2.market.entity.Order;
import com.team2.market.entity.Post;
import com.team2.market.entity.Seller;
import com.team2.market.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findAllByUserAndOrderType(User user, OrderResultType type, Pageable pageable);

	@Query("select o from orders o where o.post.seller=:seller and o.post.forSale=:type")
	List<Order> findAllBySellerAndSaleType(@Param("seller") Seller seller,
		@Param("type") SaleResultType type, Pageable pageable);
}
