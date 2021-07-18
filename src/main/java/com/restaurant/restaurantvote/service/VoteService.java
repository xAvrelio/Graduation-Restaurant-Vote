package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Vote;
import com.restaurant.restaurantvote.repository.RestaurantRepository;
import com.restaurant.restaurantvote.repository.UserRepository;
import com.restaurant.restaurantvote.repository.VoteRepository;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import com.restaurant.restaurantvote.util.exception.ToLateToVoteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoteService {

    public static LocalTime limitVoteTime = LocalTime.of(11, 0, 0);
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vote vote(int restaurantId, int userId) {
        //That part need to moved to controller
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(limitVoteTime)) {
            throw new ToLateToVoteException("to late to vote");
        }
        boolean existsById = restaurantRepository.existsById(restaurantId);
        if (!existsById) {
            throw new NotFoundException("can't find a restaurant with id=" + restaurantId);
        } else {
            Vote vote = voteRepository.findByDate(LocalDate.now()).orElse(new Vote());
            vote.setRestaurant(restaurantRepository.getById(restaurantId));
            vote.setUser(userRepository.getById(userId));
            return voteRepository.save(vote);
        }
    }

    public List<Vote> findByUserIdBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        return voteRepository.findByUserIdAndDateBetweenOrderByDate(userId, startDate, endDate);
    }

    public List<Vote> findByRestaurantIdBetweenDates(int restaurantId, LocalDate startDate, LocalDate endDate) {
        boolean existsById = restaurantRepository.existsById(restaurantId);
        if (!existsById) {
            throw new NotFoundException("can't find a restaurant with id=" + restaurantId);
        } else {
            return voteRepository.findByRestaurantIdAndDateBetweenOrderByDate(restaurantId, startDate, endDate);
        }
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public List<Vote> findAllBetweenDates(LocalDate startDate, LocalDate endDate) {
        return voteRepository.findAllByDateBetweenOrderByDate(startDate, endDate);
    }


}
