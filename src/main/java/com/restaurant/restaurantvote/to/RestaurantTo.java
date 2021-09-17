package com.restaurant.restaurantvote.to;


import com.restaurant.restaurantvote.model.Menu;
import com.restaurant.restaurantvote.model.Restaurant;
import lombok.Getter;

import java.util.List;

@Getter
public class RestaurantTo {
    private final int id;

    private final String name;

    private final List<Menu> menus;


    public RestaurantTo(Restaurant restaurant, List<Menu> menu) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.menus = menu;
    }

    public RestaurantTo(int id, String name, List<Menu> menus) {
        this.id = id;
        this.name = name;
        this.menus = menus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantTo that = (RestaurantTo) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        return menus != null ? menus.equals(that.menus) : that.menus == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (menus != null ? menus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menus=" + menus +
                '}';
    }
}
