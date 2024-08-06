package com.fininco.finincoserver.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(nullable = false)
    private String id;

    @Embedded
    private AccountInfo accountInfo;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public User(String id, AccountInfo accountInfo, String nickname) {
        this.id = id;
        this.accountInfo = accountInfo;
        this.nickname = nickname;
    }

}
