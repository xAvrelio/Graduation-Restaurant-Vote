package com.restaurant.restaurantvote.repository;

import com.restaurant.restaurantvote.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int deleteById(int id);

    Optional<User> getByEmail(String email);


}
