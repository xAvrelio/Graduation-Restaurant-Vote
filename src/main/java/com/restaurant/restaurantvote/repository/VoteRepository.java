package com.restaurant.restaurantvote.repository;

import com.restaurant.restaurantvote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByDate(LocalDate date);

    Vote save(Vote vote);

    List<Vote> findAllByDateBetweenOrderByDate(LocalDate startDate, LocalDate endDate);

    List<Vote> findByUserIdAndDateBetweenOrderByDate(int userId, LocalDate startDate, LocalDate endDate);

    List<Vote> findByRestaurantIdAndDateBetweenOrderByDate(int restaurantId, LocalDate startDate, LocalDate endDate);

    int countVoteByRestaurantId(int restaurantId);

    int countVoteByRestaurantIdAndDateBetween(int restaurantId, LocalDate startDate, LocalDate endDate);

}
