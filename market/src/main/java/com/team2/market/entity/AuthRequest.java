package com.team2.market.entity;

import javax.persistence.*;

import com.team2.market.entity.types.RequestStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class AuthRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @OneToOne
    private User user;

    @Column
    //@Enumerated(value = EnumType.STRING)
    private RequestStatus status;
    
    // 주문을 요청한 시간 추가해야할듯

    public AuthRequest(User user, RequestStatus status) {
        this.user = user;
        this.status = status;
    }

    public void setState(RequestStatus status) {
        this.status = status;
    }
}
