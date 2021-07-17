package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Role;
import com.restaurant.restaurantvote.model.User;
import com.restaurant.restaurantvote.util.ValidationUtil;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTestData {
    public static final MatcherFactory<User> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("registered");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setPassword("newPass");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    public static <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        assertThrows(rootExceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw ValidationUtil.getRootCause(e);
            }
        });
    }
}
