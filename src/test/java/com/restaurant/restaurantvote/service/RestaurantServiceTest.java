package com.restaurant.restaurantvote.service;


import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.to.RestaurantTo;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.restaurant.restaurantvote.service.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void getRestaurantsWithLunchesByDate() {
        List<RestaurantTo> list = service.getWithLunchesByDate(FOUND_DATE_START);
        MATCHER_TO.assertMatch(list, ALL_RESTAURANTS_TO);
    }

    @Test
    void getRestaurantsWithLunchesByDateEmpty() {
        List<RestaurantTo> list = service.getWithLunchesByDate(NOT_FOUND_DATE);
        MATCHER_TO.assertMatch(list, List.of(withLunchesTo(RESTAURANT1, Collections.emptyList()), withLunchesTo(RESTAURANT2, Collections.emptyList()), withLunchesTo(RESTAURANT3, Collections.emptyList())));
    }

    @Test
    void getRestaurantsWithLunchesBetweenDates() {
        List<RestaurantTo> list = service.getWithLunchesBetweenDates(FOUND_DATE_START, FOUND_DATE_END);
        MATCHER_TO.assertMatch(list, ALL_RESTAURANTS_TO);
    }

    @Test
    void getRestaurantsWithLunchesBetweenDatesEmpty() {
        List<RestaurantTo> list = service.getWithLunchesBetweenDates(NOT_FOUND_DATE, NOT_FOUND_DATE);
        MATCHER_TO.assertMatch(list, List.of(withLunchesTo(RESTAURANT1, Collections.emptyList()), withLunchesTo(RESTAURANT2, Collections.emptyList()), withLunchesTo(RESTAURANT3, Collections.emptyList())));
    }

    @Test
    void delete() {
        service.deleteById(RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> service.findById(RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.deleteById(NOT_FOUND));
    }

    @Test
    public void create() {
        Restaurant created = service.save(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        MATCHER.assertMatch(created, newRestaurant);
        MATCHER.assertMatch(service.findById(newId), newRestaurant);
    }

    @Test
    public void createDuplicate() {
        assertThrows(DataAccessException.class, () ->
                service.save(RESTAURANT_DUPLICATE));
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        service.save(updated);
        MATCHER.assertMatch(service.findById(RESTAURANT_ID), getUpdated());
    }

    @Test
    void findById() {
        Restaurant actual = service.findById(RESTAURANT_ID);
        MATCHER.assertMatch(actual, RESTAURANT1);
    }

    @Test
    void findByIdNotFound() {
        assertThrows(NotFoundException.class, () -> service.findById(NOT_FOUND));
    }

    @Test
    void getByIdWithLunchs() {
        Restaurant actual = service.findByIdWithLunchs(RESTAURANT_ID);
        MATCHER.assertMatch(actual, RESTAURANT1);
        LunchTestData.MATCHER.assertMatch(actual.getLunches(), RESTAURANT1_LUNCHES);
    }

    @Test
    void getAllWithLunches() {
        List<Restaurant> actual = service.findAllWithLunches();
        List<Restaurant> excepted = List.of(withLunches(RESTAURANT1, RESTAURANT1_LUNCHES), withLunches(RESTAURANT2, RESTAURANT2_LUNCHES), withLunches(RESTAURANT3, Collections.emptyList()));
        MATCHER.assertMatch(actual, ALL_RESTAURANTS);
        actual.forEach(restaurant -> LunchTestData.MATCHER.assertMatch(restaurant.getLunches(), excepted.get(excepted.indexOf(restaurant)).getLunches()));
    }
}