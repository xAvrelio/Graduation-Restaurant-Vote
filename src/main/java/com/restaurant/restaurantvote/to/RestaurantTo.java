package com.restaurant.restaurantvote.to;

import com.restaurant.restaurantvote.model.Lunch;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
public class RestaurantTo {
    private int id;
    private String name;
    private List<Lunch> lunchs;

}
