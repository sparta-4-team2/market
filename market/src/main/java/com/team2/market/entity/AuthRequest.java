package com.team2.market.entity;

import javax.persistence.*;

import com.team2.market.entity.types.RequestType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class AuthRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn
    @OneToOne
    private User user;

    @Column
    @Enumerated(value = EnumType.STRING)
    private RequestType type;
    
    // 주문을 요청한 시간 추가해야할듯

    public AuthRequest(User user, RequestType type) {
        this.user = user;
        this.type = type;
    }
}
