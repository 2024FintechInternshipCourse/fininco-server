package com.fininco.finincoserver.user.auth.oAuth.service;

import com.fininco.finincoserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    default User getById(String id) {
        return findById(id).orElseThrow(IllegalArgumentException::new);
    }

    boolean existsById(String id);

}
