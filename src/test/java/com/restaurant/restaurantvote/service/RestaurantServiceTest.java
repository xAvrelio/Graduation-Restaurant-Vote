package com.restaurant.restaurantvote.service;


import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.service.TestData.MenuTestData;
import com.restaurant.restaurantvote.to.RestaurantTo;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

import static com.restaurant.restaurantvote.service.TestData.MenuTestData.RESTAURANT_ID;
import static com.restaurant.restaurantvote.service.TestData.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

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
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        MATCHER.assertMatch(created, newRestaurant);
        MATCHER.assertMatch(service.findById(newId), newRestaurant);
    }

    @Test
    public void createDuplicate() {
        assertThrows(DataAccessException.class, () ->
                service.create(RESTAURANT_DUPLICATE));
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        Restaurant restaurant = service.findById(RESTAURANT_ID);
        MATCHER.assertMatch(restaurant, updated);
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
    void findAllWithMenuBetweenDates() {
        List<RestaurantTo> actual = service.findAllWithMenuBetweenDates(ThreeMenuDate, TwoMenuDate);
        List<RestaurantTo> expected = List.of(
                new RestaurantTo(RESTAURANT1, List.of(MenuTestData.MENU, MenuTestData.MENU3)),
                new RestaurantTo(RESTAURANT2, List.of(MenuTestData.MENU1, MenuTestData.MENU4)),
                new RestaurantTo(RESTAURANT3, List.of(MenuTestData.MENU2))
        );
        MATCHER_TO.assertMatch(actual, expected);
    }

    @Test
    void findAllWithMenuNullDates() {
        List<RestaurantTo> actual = service.findAllWithMenuBetweenDates(null, null);
        List<RestaurantTo> expected = List.of(
                new RestaurantTo(RESTAURANT1, List.of(MenuTestData.MENU, MenuTestData.MENU3, MenuTestData.MENU_NOW)),
                new RestaurantTo(RESTAURANT2, List.of(MenuTestData.MENU1, MenuTestData.MENU4)),
                new RestaurantTo(RESTAURANT3, List.of(MenuTestData.MENU2))
        );
        MATCHER_TO.assertMatch(actual, expected);
    }

    @Test
    void findAllWithMenuByDate() {
        List<RestaurantTo> actual = service.findAllWithMenuByDate(TwoMenuDate);
        List<RestaurantTo> expected = List.of(
                new RestaurantTo(RESTAURANT1, List.of(MenuTestData.MENU3)),
                new RestaurantTo(RESTAURANT2, List.of(MenuTestData.MENU4)),
                new RestaurantTo(RESTAURANT3, Collections.emptyList())
        );
        MATCHER_TO.assertMatch(actual, expected);
    }

    @Test
    void findAllWithMenuByNullDate() {
        List<RestaurantTo> actual = service.findAllWithMenuByDate(null);
        List<RestaurantTo> expected = List.of(
                new RestaurantTo(RESTAURANT1, List.of(MenuTestData.MENU_NOW)),
                new RestaurantTo(RESTAURANT2, Collections.emptyList()),
                new RestaurantTo(RESTAURANT3, Collections.emptyList())
        );
        MATCHER_TO.assertMatch(actual, expected);
    }

    @Test
    void createWithEmptyName() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "")));
    }

    @Test
    void createWithMinSizeName() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "1")));
    }


}