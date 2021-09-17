package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Vote;
import com.restaurant.restaurantvote.service.TestData.RestaurantTestData;
import com.restaurant.restaurantvote.service.TestData.UserTestData;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.restaurant.restaurantvote.service.TestData.VoteTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;


    @Test
    void notFoundRestaurant() {
        assertThrows(NotFoundException.class, () -> service.vote(RestaurantTestData.NOT_FOUND, UserTestData.USER_ID));
    }


    @Test
    void createVote() {
        Vote created = service.vote(RESTAURANT_ID, USER_ID);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(service.findById(newId, USER_ID), newVote);
    }

    @Test
    void updateVote() {
        Vote updated = getUpdated();
        service.vote(updated.getRestaurant().id(), updated.getUser().id());
        Vote vote = service.findById(VOTE_ID, USER_ID);
        assertThat(vote.getRestaurant().getId()).isEqualTo(updated.getRestaurant().getId());
        MATCHER.assertMatch(vote, updated);

    }

//    @Test
//    void createVoteAfterTime() {
//        ClockUtil clockUtil = ClockUtil.getInstance();
//        Clock clock = Clock.fixed(Instant.parse("2021-07-03T12:00:01.00Z"), ZoneId.of("UTC"));
//        clockUtil.setClock(clock);
//        assertThrows(ToLateToVoteException.class, () -> service.vote(RESTAURANT_ID, UserTestData.USER_ID));
//        clockUtil.setClock(Clock.systemDefaultZone());
//    }




}
