package com.restaurant.restaurantvote.to;


import com.restaurant.restaurantvote.model.Menu;
import com.restaurant.restaurantvote.model.Restaurant;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RestaurantTo {
    private final int id;
    private final String name;
    private Menu menu;
    private List<Menu> menus = new ArrayList<>();

    public RestaurantTo(Restaurant restaurant, Menu menu){
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.menu = menu;
    }

    public RestaurantTo(Restaurant restaurant, List<Menu> menu) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.menus = menu;
    }

    public RestaurantTo(int id, String name, Menu menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
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
        if (!menu.equals(that.menu)) return false;
        return menus != null ? menus.equals(that.menus) : that.menus == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RestaurantTo{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name).append('\'');
        if (menus.isEmpty()) {
            sb.append(", menu=").append(menu);
        } else {
            sb.append(", menus=").append(menus);
        }
        sb.append('}');
        return sb.toString();
    }
}
