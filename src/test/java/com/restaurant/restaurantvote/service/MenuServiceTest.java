package com.restaurant.restaurantvote.service;


import com.restaurant.restaurantvote.model.Menu;
import com.restaurant.restaurantvote.service.TestData.LunchTestData;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.restaurant.restaurantvote.service.TestData.MenuTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Autowired
    private LunchService lunchService;

    @Test
    void deleteById() {
        service.deleteById(MENU_ID);
        assertThrows(NotFoundException.class, () -> service.findById(MENU_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.deleteById(NOT_FOUND));
    }

    @Test
    void create() {
        Menu created = service.save(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Menu newMenu = getNew();
        newMenu.setId(newId);
        MATCHER.assertMatch(created, newMenu);
        MATCHER.assertMatch(service.findById(newId), newMenu);
    }

    @Test
    void update() {
        Menu updated = getUpdated();
        service.save(updated, RESTAURANT_ID);
        MATCHER.assertMatch(service.findById(MENU_ID), getUpdated());
    }

    @Test
    public void createDuplicate() {
        assertThrows(DataAccessException.class, () ->
                service.save(MENU_DUPLICATED, RESTAURANT_ID));
    }

    @Test
    void findById() {
        MATCHER.assertMatch(service.findById(MENU_ID), MENU);
    }

    @Test
    void findByIdNotFound() {
        assertThrows(NotFoundException.class, () -> service.findById(NOT_FOUND));
    }

    @Test
    void findAllByRestaurant_id() {
        List<Menu> actual = service.findAllByRestaurantId(RESTAURANT_ID);
        MATCHER.assertMatch(actual, List.of(MENU, MENU3, MENU_NOW));
    }

    @Test
    void findAllByDateWithLunches() {
        List<Menu> actual = service.findAllByDateWithLunches(ThreeMenuDate);
        MATCHER.assertMatch(actual, List.of(MENU,MENU1,MENU2));
        /*
               check lunches
         */
        actual.forEach(menu -> {
            LunchTestData.MATCHER.assertMatch(menu.getLunchs(), lunchService.findByMenuId(menu.getId()));
        });

    }
    @Test
    void findAllByDateWithLunchesWithNullDate() {
        List<Menu> actual = service.findAllByDateWithLunches(null);
        MATCHER.assertMatch(actual, List.of(MENU_NOW));
        /*
               check lunches
         */
        actual.forEach(menu -> {
            LunchTestData.MATCHER.assertMatch(menu.getLunchs(), lunchService.findByMenuId(menu.getId()));
        });

    }


    @Test
    void findAllWithLunchesBetweenDates() {
        List<Menu> actual = service.findAllBetweenDates(ThreeMenuDate, TwoMenuDate);
        MATCHER.assertMatch(actual, List.of(MENU,MENU1,MENU2,MENU3,MENU4));
        /*
               check lunches
         */
        actual.forEach(menu -> {
            LunchTestData.MATCHER.assertMatch(menu.getLunchs(), lunchService.findByMenuId(menu.getId()));
        });

    }

    @Test
    void findAllWithLunchesBetweenNullDates() {
        List<Menu> actual = service.findAllBetweenDates(null, null);
        MATCHER.assertMatch(actual, List.of(MENU,MENU1,MENU2,MENU3,MENU4,MENU_NOW));
        /*
               check lunches
         */
        actual.forEach(menu -> {
            LunchTestData.MATCHER.assertMatch(menu.getLunchs(), lunchService.findByMenuId(menu.getId()));
        });

    }

    @Test
    void createWithNullDate() {
        validateRootCause(ConstraintViolationException.class, () -> service.save(new Menu(null,null), RESTAURANT_ID));
    }

}