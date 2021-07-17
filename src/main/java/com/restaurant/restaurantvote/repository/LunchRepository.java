package com.restaurant.restaurantvote.repository;

import com.restaurant.restaurantvote.model.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface LunchRepository extends JpaRepository<Lunch, Integer> {

    List<Lunch> findLunchByDate(LocalDate date);

    List<Lunch> findLunchByDateBetween(LocalDate startDate, LocalDate endDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Lunch l WHERE l.id=:id")
    int deleteById(int id);

}
