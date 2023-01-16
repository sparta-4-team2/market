package com.team2.market.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn
    @OneToOne
    private Product product;

    @Column
    private String contents;

    @OneToMany(mappedBy = "post")
    private List<Order> orderlist = new ArrayList<>();

    public Post(Product product, String contents) {
        this.product = product;
        this.contents = contents;
    }
}
