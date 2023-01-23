package com.team2.market.entity;

import javax.persistence.*;

import com.team2.market.dto.users.request.ProfileUpdateRequestDto;
import com.team2.market.entity.types.UserRoleType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username; // 로그인시 사용하는 ID
    
    @Column
    private String password;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleType role;

    @Column
    private String nickname; // 어플리케이션 내부 글 작성시 표기되는 별명

    @Column
    private String profileImg;

    public User(String username, String password, UserRoleType role) {
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

    public void updateRole(UserRoleType role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role.equals(UserRoleType.ROLE_ADMIN);
    }
}
