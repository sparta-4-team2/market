package com.team2.market.entity;

import javax.persistence.*;

import lombok.NoArgsConstructor;

@Entity(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
