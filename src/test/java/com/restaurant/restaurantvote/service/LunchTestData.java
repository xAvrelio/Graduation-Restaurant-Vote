package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class LunchTestData {
    public static final MatcherFactory<Lunch> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("restaurant");
    public static final int NOT_FOUND = 10;
    public static final LocalDate NOT_FOUND_DATE = LocalDate.of(2000, 7, 1);
    public static final LocalDate FOUND_DATE_START = LocalDate.of(2021, 7, 17);
    public static final LocalDate FOUND_DATE_END = LocalDate.of(2021, 7, 17);
    public static final int LUNCH_ID = 1;
    public static final int RESTAURANT_ID = 1;

    public static final Lunch LUNCH1 = new Lunch(1, "Sandwich", BigDecimal.valueOf(2.55), LocalDate.of(2021, 7, 17));
    public static final Lunch LUNCH2 = new Lunch(2, "Some food", BigDecimal.valueOf(4.99), LocalDate.of(2021, 7, 17));
    public static final Lunch LUNCH3 = new Lunch(3, "Some drink", BigDecimal.valueOf(1.25), LocalDate.of(2021, 7, 17));
    public static final Lunch LUNCH4 = new Lunch(4, "Hamburger", BigDecimal.valueOf(3.44), LocalDate.of(2021, 7, 17));
    public static final Lunch LUNCH5 = new Lunch(5, "Pizza", BigDecimal.valueOf(6.01), LocalDate.of(2021, 7, 17));
    public static final Lunch LUNCH_DUPLICATED = new Lunch(null, "Sandwich", BigDecimal.valueOf(2.55), LocalDate.of(2021, 7, 17));

    public static final List<Lunch> ALL_LUNCHES = List.of(LUNCH1, LUNCH2, LUNCH3, LUNCH4, LUNCH5);

    public static Lunch getNew() {
        return new Lunch(null, "new Food", BigDecimal.valueOf(3.4), LocalDate.now());
    }

    public static Lunch getUpdated() {
        return new Lunch(LUNCH_ID, "updated lunch", BigDecimal.valueOf(2.3), LocalDate.of(2021, 7, 12));
    }


}
