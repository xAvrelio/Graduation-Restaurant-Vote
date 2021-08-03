package com.restaurant.restaurantvote.repository;

import com.restaurant.restaurantvote.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Query("DELETE FROM Restaurant r where r.id=:id")
    int deleteById(int id);

}
