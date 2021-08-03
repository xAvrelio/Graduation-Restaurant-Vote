package com.restaurant.restaurantvote.service.TestData;

import com.restaurant.restaurantvote.model.Vote;
import com.restaurant.restaurantvote.service.MatcherFactory;

public class VoteTestData {
    public static final MatcherFactory<Vote> MATCHER = MatcherFactory.usingIgnoringFieldsComparator("user");

    public static final Integer RESTAURANT_ID = 1;
    public static final Integer USER_ID = 1;

    public static Vote getNew() {
        return new Vote(null, RESTAURANT_ID);
    }

}
