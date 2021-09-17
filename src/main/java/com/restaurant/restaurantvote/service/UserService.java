package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.User;
import com.restaurant.restaurantvote.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFound;
import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }


    @Transactional
    public void deleteById(int id) {
        checkNotFoundWithId(repository.deleteById(id) != 0, id);
    }

    public User findById(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email).orElse(null), "email=" + email);
    }

    public List<User> getAll() {
        return repository.findAll();
    }


}
