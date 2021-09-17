package com.restaurant.restaurantvote;

import com.restaurant.restaurantvote.model.User;
import com.restaurant.restaurantvote.to.UserTo;
import com.restaurant.restaurantvote.util.UserUtil;
import org.springframework.lang.NonNull;

import java.io.Serial;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthUser(@NonNull User user) {
    super(user.getEmail(), user.getPassword(), user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }


    public int getId() {
        return userTo.getId();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }

}