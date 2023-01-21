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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JoinColumn(name="user_id")
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "seller")
    private List<Post> posts = new ArrayList<>();

    public Seller(User user) {
        this.user = user;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public Long getUserId() {
        return this.user.getId();
    }
}
