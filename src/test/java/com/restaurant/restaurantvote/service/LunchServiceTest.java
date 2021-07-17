package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import static org.junit.jupiter.api.Assertions.*;
import static com.restaurant.restaurantvote.service.LunchTestData.*;

@SpringBootTest
class LunchServiceTest {

    @Autowired
    private LunchService service;

    @Test
    void delete() {
        service.delete(LUNCH_ID);
        assertThrows(NotFoundException.class, () -> service.findById(LUNCH_ID));
    }
    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void create() {
        Lunch created = service.save(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Lunch newLunch = getNew();
        newLunch.setId(newId);
        MATCHER.assertMatch(created, newLunch);
        MATCHER.assertMatch(service.findById(newId), newLunch);
    }

    @Test
    public void update() {
        Lunch updated = getUpdated();
        service.save(updated, RESTAURANT_ID);
        MATCHER.assertMatch(service.findById(LUNCH_ID), getUpdated());
    }

    @Test
    void findById() {
        Lunch actual = service.findById(LUNCH_ID);
        LunchTestData.MATCHER.assertMatch(actual, LUNCH1);
    }

    @Test
    void findByIdNotFound() {
        assertThrows(NotFoundException.class, () -> service.findById(LunchTestData.NOT_FOUND));
    }

    @Test
    void findByDate() {
    }

    @Test
    void findBetweenDates() {
    }
}