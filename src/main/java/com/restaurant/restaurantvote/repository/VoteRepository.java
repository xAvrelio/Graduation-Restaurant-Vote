package com.restaurant.restaurantvote.repository;

import com.restaurant.restaurantvote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.date=:date and v.user.id=:userId")
    Optional<Vote> findByDateAndUserId(LocalDate date, int userId);

    @Query("SELECT v FROM Vote v WHERE v.id=:id and v.user.id=:userId")
    Optional<Vote> findByIdAndUserId(int id, int userId);

    List<Vote> findAllByDateBetween(LocalDate startDate, LocalDate endDate);


}
