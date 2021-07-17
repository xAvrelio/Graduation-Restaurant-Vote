package com.restaurant.restaurantvote.service;


import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.restaurant.restaurantvote.service.LunchTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("lunches");
    public static final MatcherFactory<RestaurantTo> MATCHER_TO = MatcherFactory.usingIgnoringFieldsComparator();

    public static final int NOT_FOUND = 10;
    public static final LocalDate NOT_FOUND_DATE = LocalDate.of(2000, 7, 1);
    public static final LocalDate FOUND_DATE_START = LocalDate.of(2021, 7, 17);
    public static final LocalDate FOUND_DATE_END = LocalDate.of(2021, 7, 17);
    public static final int RESTAURANT_ID = 1;
    public static final Restaurant RESTAURANT_DUPLICATE = new Restaurant(null, "La Plaza");
    public static final List<Lunch> RESTAURANT1_LUNCHES = List.of(LUNCH1, LUNCH2, LUNCH3);
    public static final List<Lunch> RESTAURANT2_LUNCHES = List.of(LUNCH4, LUNCH5);
    public static final List<RestaurantTo> ALL_RESTAURANTS_TO = List.of(new RestaurantTo(1, "La Plaza", RESTAURANT1_LUNCHES), new RestaurantTo(2, "Dedushka", RESTAURANT2_LUNCHES), new RestaurantTo(3, "Saccana", Collections.emptyList()));
    public static final Restaurant RESTAURANT1 = new Restaurant(1, "La Plaza");
    public static final Restaurant RESTAURANT2 = new Restaurant(2, "Dedushka");
    public static final Restaurant RESTAURANT3 = new Restaurant(3, "Saccana");
    public static final List<Restaurant> ALL_RESTAURANTS = List.of(RESTAURANT1, RESTAURANT2, RESTAURANT3);

    public static Restaurant withLunches(Restaurant restaurant, List<Lunch> lunches) {
        restaurant.setLunches(lunches);
        return restaurant;
    }

    public static RestaurantTo withLunchesTo(Restaurant restaurant, List<Lunch> lunches) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), lunches);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "new Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "updated Restaurant");
    }


}
