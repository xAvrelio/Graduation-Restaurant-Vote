package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class VoteServiceTest {

    @Autowired
    private VoteService service;


    @Test
    void notFoundRestaurant() {
        assertThrows(NotFoundException.class, () -> service.vote(RestaurantTestData.NOT_FOUND, UserTestData.USER_ID));
    }
}
