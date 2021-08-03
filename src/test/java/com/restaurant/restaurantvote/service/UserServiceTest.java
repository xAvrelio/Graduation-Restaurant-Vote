package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Role;
import com.restaurant.restaurantvote.model.User;
import com.restaurant.restaurantvote.service.TestData.UserTestData;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.restaurant.restaurantvote.service.TestData.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    public void getAll() {
        List<User> all = service.getAll();
        MATCHER.assertMatch(all, user, admin);
    }

    @Test
    public void create() {
        User created = service.save(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        MATCHER.assertMatch(created, newUser);
        MATCHER.assertMatch(service.findById(newId), newUser);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.save(new User(null, "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    public void delete() {
        service.deleteById(USER_ID);
        assertThrows(NotFoundException.class, () -> service.findById(USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.deleteById(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = service.findById(USER_ID);
        MATCHER.assertMatch(user, UserTestData.user);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.findById(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        MATCHER.assertMatch(user, admin);
    }

    @Test
    public void update() {
        User updated = getUpdated();
        service.save(updated);
        MATCHER.assertMatch(service.findById(USER_ID), getUpdated());
    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.save(new User(null, " ", "password", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.save(new User(null, "mail@yandex.ru", "  ", Role.USER)));
    }
}