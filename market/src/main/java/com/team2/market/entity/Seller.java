package com.team2.market.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @JoinColumn
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "seller")
    private List<Post> posts = new ArrayList<>();
}
