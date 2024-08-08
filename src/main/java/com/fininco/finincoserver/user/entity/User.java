package com.fininco.finincoserver.user.entity;

import com.fininco.finincoserver.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public User(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

}
