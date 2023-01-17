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

    @Column
    private String productName;

    @Column 
    private int price;

    @Column
    private String contents;

    @OneToMany(mappedBy = "post")
    private List<Order> orderlist = new ArrayList<>();

    public Post(String productName, int price, String contents) {
        this.productName = productName;
        this.price = price;
        this.contents = contents;
    }

}
