package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.restaurant.restaurantvote.service.LunchTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class LunchServiceTest {

    @Autowired
    private LunchService service;

    @Test
    void delete() {
        service.deleteById(LUNCH_ID);
        assertThrows(NotFoundException.class, () -> service.findById(LUNCH_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.deleteById(NOT_FOUND));
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
    public void createDuplicate() {
        assertThrows(DataAccessException.class, () ->
                service.save(LUNCH_DUPLICATED, RESTAURANT_ID));
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
        assertThrows(NotFoundException.class, () -> service.findById(NOT_FOUND));
    }

    @Test
    void findByDate() {
        List<Lunch> lunches = service.findByDate(LocalDate.now());
        MATCHER.assertMatch(lunches, ALL_LUNCHES);
    }

    @Test
    void findByDateEmpty() {
        List<Lunch> lunches = service.findByDate(NOT_FOUND_DATE);
        MATCHER.assertMatch(lunches, Collections.emptyList());
    }

    @Test
    void findBetweenDates() {
        List<Lunch> lunches = service.findBetweenDates(FOUND_DATE_START, FOUND_DATE_END);
        MATCHER.assertMatch(lunches, ALL_LUNCHES);
    }

    @Test
    void findBetweenDatesEmpty() {
        List<Lunch> lunches = service.findBetweenDates(NOT_FOUND_DATE, NOT_FOUND_DATE);
        MATCHER.assertMatch(lunches, Collections.emptyList());
    }
}