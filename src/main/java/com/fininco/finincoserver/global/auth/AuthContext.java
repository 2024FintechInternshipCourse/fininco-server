package com.fininco.finincoserver.global.auth;

import com.fininco.finincoserver.user.entity.User;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class AuthContext {

    private User user;


    public void registerAuth(User user) {
        this.user = user;
    }

}
