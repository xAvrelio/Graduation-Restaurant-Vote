package com.restaurant.restaurantvote.service.TestData;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.service.MatcherFactory;

import java.math.BigDecimal;
import java.util.List;

public class LunchTestData {
    public static final MatcherFactory<Lunch> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("menu");
    public static final int NOT_FOUND = 22;
    public static final int LUNCH_ID = 1;
    public static final int MENU_ID = 1;

    public static final Lunch LUNCH1 = new Lunch(1, "Sandwich", BigDecimal.valueOf(2.55));
    public static final Lunch LUNCH2 = new Lunch(2, "Some food", BigDecimal.valueOf(4.99));
    public static final Lunch LUNCH3 = new Lunch(3, "Some drink", BigDecimal.valueOf(1.25));
    public static final Lunch LUNCH4 = new Lunch(4, "Hamburger", BigDecimal.valueOf(3.44));
    public static final Lunch LUNCH5 = new Lunch(5, "Pizza1", BigDecimal.valueOf(6.01));
    public static final Lunch LUNCH6 = new Lunch(6, "Pizza2", BigDecimal.valueOf(6.01));
    public static final Lunch LUNCH7 = new Lunch(7, "Pizza3", BigDecimal.valueOf(6.01));
    public static final Lunch LUNCH8 = new Lunch(8, "Pizza33", BigDecimal.valueOf(6.01));
    public static final Lunch LUNCH9 = new Lunch(9, "Pizza22", BigDecimal.valueOf(6.01));
    public static final Lunch LUNCH10 = new Lunch(10, "Pizza31", BigDecimal.valueOf(6.01));
    public static final Lunch LUNCH11 = new Lunch(11, "Pie", BigDecimal.valueOf(4.55));

    public static final Lunch LUNCH_DUPLICATED = new Lunch(null, "Sandwich", BigDecimal.valueOf(2.55));

    public static final List<Lunch> ALL_LUNCHES = List.of(LUNCH1, LUNCH2, LUNCH3, LUNCH4, LUNCH5, LUNCH6, LUNCH7, LUNCH8, LUNCH9, LUNCH10, LUNCH11);
    public static final List<Lunch> LUNCHES_MENU1 = List.of(LUNCH1, LUNCH2);


    public static Lunch getNew() {
        return new Lunch(null, "new Food", BigDecimal.valueOf(3.4));
    }

    public static Lunch getUpdated() {
        return new Lunch(LUNCH_ID, "updated lunch", BigDecimal.valueOf(1.3));
    }


}
