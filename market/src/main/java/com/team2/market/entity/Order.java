package com.team2.market.entity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.persistence.*;

import com.team2.market.dto.types.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Entity(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private OffsetDateTime tradeStartTime;

    private OffsetDateTime tradeEndTime = OffsetDateTime.of(1, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

    public Order(Post post, User user) {
        this.post = post;
        this.user = user;
        this.tradeStartTime = OffsetDateTime.now();
        this.status = OrderStatus.IN_PROGRESS;
    }

	public void successOrder() {
        this.status = OrderStatus.SUCCESS;
	}
}
