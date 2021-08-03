package com.restaurant.restaurantvote.util;

import java.time.Clock;


public class ClockUtil {
    private static ClockUtil instance;
    private Clock clock = Clock.systemDefaultZone();

    private ClockUtil() {
    }

    public static ClockUtil getInstance() {
        if (instance == null) {
            instance = new ClockUtil();
        }
        return instance;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
