package com.restaurant.restaurantvote.repository;

import com.restaurant.restaurantvote.model.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LunchRepository extends JpaRepository<Lunch, Integer> {

    @Modifying
    @Query("DELETE FROM Lunch l WHERE l.id=:id")
    int deleteById(int id);

    List<Lunch> findAllByMenu_Id(int menuId);
}
