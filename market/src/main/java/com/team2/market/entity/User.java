package com.team2.market.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username; // 로그인시 사용하는 ID
    
    @Column
    private String password;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column
    private String nickname; // 어플리케이션 내부 글 작성시 표기되는 별명

    @Column
    private String profileImg;

    @OneToMany
    private List<Order> orderlist = new ArrayList<>();


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void updateProfile(ProfileUpdateRequestDto requestDto) {
        this.nickname = requestDto.getNickName();
        this.profileImg = requestDto.getProfileImg();
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
}
