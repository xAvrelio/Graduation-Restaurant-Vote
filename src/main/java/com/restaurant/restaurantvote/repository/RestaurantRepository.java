package com.restaurant.restaurantvote.repository;

import com.restaurant.restaurantvote.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r where r.id=:id")
    int deleteById(int id);

    @EntityGraph(attributePaths = "lunches")
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.lunches WHERE r.id=:id")
    Restaurant findByIdWithLunches(int id);

    @EntityGraph(attributePaths = "lunches")
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.lunches")
    List<Restaurant> findByIdWithLunches();


}
