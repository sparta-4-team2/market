package com.team2.market.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.HashMap;

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

    @ElementCollection
    @CollectionTable(
        name = "posttoseller",
        joinColumns = @JoinColumn(name = "seller_id")
    )
    @MapKeyColumn(name ="post_id")
    private Map<Long, Post> postToSeller = new HashMap<>();

    public Seller(User user) {
        this.user = user;
    }

    public void addPost(Long postId, Post post) {
        this.postToSeller.put(postId, post);
    }

    public Long getUserId() {
        return this.user.getId();
    }
}
