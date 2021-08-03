package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Vote;
import com.restaurant.restaurantvote.repository.RestaurantRepository;
import com.restaurant.restaurantvote.repository.UserRepository;
import com.restaurant.restaurantvote.repository.VoteRepository;
import com.restaurant.restaurantvote.util.ClockUtil;
import com.restaurant.restaurantvote.util.DateTimeUtil;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import com.restaurant.restaurantvote.util.exception.ToLateToVoteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class VoteService {


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
        ClockUtil clockUtil = ClockUtil.getInstance();
        LocalTime currentTime = LocalTime.now(clockUtil.getClock());
        if (currentTime.isAfter(DateTimeUtil.limitVoteTime)) {
            throw new ToLateToVoteException("to late to vote serverTime=" + currentTime);
        }
        boolean existsById = restaurantRepository.existsById(restaurantId);
        if (!existsById) {
            throw new NotFoundException("can't find a restaurant with id=" + restaurantId);
        } else {
            Vote vote = voteRepository.findByDateAndUserId(LocalDate.now(), userId).orElse(new Vote(null, restaurantId));
            vote.setUser(userRepository.getById(userId));
            return voteRepository.save(vote);
        }
    }

    public Vote findById(int id, int userId) {
        return checkNotFoundWithId(voteRepository.findByIdAndUserId(id, userId).orElse(null), id);
    }


}
