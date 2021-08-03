package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;

import static com.restaurant.restaurantvote.service.TestData.LunchTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class LunchServiceTest extends AbstractServiceTest {

    @Autowired
    private LunchService service;


    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.deleteById(NOT_FOUND));
    }

    @Test
    public void createDuplicate() {
        assertThrows(DataAccessException.class, () ->
                service.save(LUNCH_DUPLICATED, MENU_ID));
    }


    @Test
    void findById() {
        Lunch actual = service.findById(LUNCH_ID);
        MATCHER.assertMatch(actual, LUNCH1);
    }

    @Test
    void findByIdNotFound() {
        assertThrows(NotFoundException.class, () -> service.findById(NOT_FOUND));
    }

    @Test
    void findAll() {
        List<Lunch> actual = service.findAll();
        MATCHER.assertMatch(actual, ALL_LUNCHES);
    }

    @Test
    void findByMenuId() {
        List<Lunch> actual = service.findByMenuId(MENU_ID);
        MATCHER.assertMatch(actual, LUNCHES_MENU1);
    }

    @Test
    void createWithExceptionEmptyName() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.save(new Lunch(null, "", BigDecimal.valueOf(1.33)), MENU_ID));
    }

    @Test
    void createWithExceptionBigDecimalFraction() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.save(new Lunch(null, "123", BigDecimal.valueOf(3333333.33)), MENU_ID));
    }

    @Test
    void createWithExceptionBigDecimalIntegerMax() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.save(new Lunch(null, "123", BigDecimal.valueOf(1.33333)), MENU_ID));

    }

    @Test
    void delete() {
        service.deleteById(LUNCH_ID);
        assertThrows(NotFoundException.class, () -> service.findById(LUNCH_ID));
    }

    @Test
    public void create() {
        Lunch created = service.save(getNew(), MENU_ID);
        int newId = created.id();
        Lunch newLunch = getNew();
        newLunch.setId(newId);
        MATCHER.assertMatch(created, newLunch);
        MATCHER.assertMatch(service.findById(newId), newLunch);
    }

    @Test
    public void update() {
        Lunch updated = getUpdated();
        Lunch actual = service.save(updated, MENU_ID);

        MATCHER.assertMatch(actual, getUpdated());
    }
}