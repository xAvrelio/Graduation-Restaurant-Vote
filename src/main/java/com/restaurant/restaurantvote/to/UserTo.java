package com.restaurant.restaurantvote.to;

import com.restaurant.restaurantvote.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserTo {

    private Integer id;

    private String email;

    private String password;

    private Set<Role> roles;

}
