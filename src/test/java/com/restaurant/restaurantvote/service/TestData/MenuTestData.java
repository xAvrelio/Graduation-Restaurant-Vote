package com.restaurant.restaurantvote.service.TestData;

import com.restaurant.restaurantvote.model.Menu;
import com.restaurant.restaurantvote.service.MatcherFactory;

import java.time.LocalDate;

public class MenuTestData {
    public static final MatcherFactory<Menu> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("restaurant", "lunchs");

    public static final int NOT_FOUND = 22;
    public static final int MENU_ID = 1;
    public static final int RESTAURANT_ID = 1;
    public static final LocalDate ThreeMenuDate = LocalDate.of(2021,7,11);
    public static final LocalDate TwoMenuDate = LocalDate.of(2021,7,17);

    public static final Menu MENU = new Menu(1, ThreeMenuDate);
    public static final Menu MENU1 = new Menu(2, ThreeMenuDate);
    public static final Menu MENU2 = new Menu(3, ThreeMenuDate);
    public static final Menu MENU3 = new Menu(4, TwoMenuDate);
    public static final Menu MENU4 = new Menu(5, TwoMenuDate);
    public static final Menu MENU_NOW = new Menu(6, LocalDate.now());

    public static final Menu MENU_DUPLICATED = new Menu(null, LocalDate.of(2021,7,11));

    public static Menu getNew() {
        return new Menu(null, LocalDate.of(2022,7,22));
    }

    public static Menu getUpdated() {
        return new Menu(MENU_ID, LocalDate.of(2022,7,11));
    }



}
