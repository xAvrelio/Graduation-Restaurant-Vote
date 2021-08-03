package com.restaurant.restaurantvote.service.TestData;


import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.service.MatcherFactory;
import com.restaurant.restaurantvote.to.RestaurantTo;

import java.time.LocalDate;


public class RestaurantTestData {
    public static final MatcherFactory<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("lunches");
    public static final MatcherFactory<RestaurantTo> MATCHER_TO = MatcherFactory.usingIgnoringFieldsComparator();

    public static final int NOT_FOUND = 10;
    public static final int RESTAURANT_ID = 1;
    public static final LocalDate ThreeMenuDate = LocalDate.of(2021, 7, 11);
    public static final LocalDate TwoMenuDate = LocalDate.of(2021, 7, 17);

    public static final Restaurant RESTAURANT1 = new Restaurant(1, "La Plaza");
    public static final Restaurant RESTAURANT2 = new Restaurant(2, "Dedushka");
    public static final Restaurant RESTAURANT3 = new Restaurant(3, "Saccana");
    public static final Restaurant RESTAURANT_DUPLICATE = new Restaurant(null, "Saccana");

    public static Restaurant getNew() {
        return new Restaurant(null, "new Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "updated Restaurant");
    }


}
