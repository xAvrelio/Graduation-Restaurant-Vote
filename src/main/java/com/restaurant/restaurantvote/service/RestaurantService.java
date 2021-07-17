package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.repository.CrudLunchRepository;
import com.restaurant.restaurantvote.repository.CrudRestaurantRepository;
import com.restaurant.restaurantvote.to.RestaurantTo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private CrudLunchRepository crudLunchRepository;

    private CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantService(CrudLunchRepository crudLunchRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudLunchRepository = crudLunchRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public List<RestaurantTo> getWithLunchesByDate(LocalDate date) {
        List<Restaurant> restaurants = crudRestaurantRepository.findAll();
        List<Lunch> lunches = crudLunchRepository.getLunchByDateOrderByPriceDesc(date);
        return RestaurantTos(restaurants, lunches);
    }

    public List<RestaurantTo> getWithLunchesBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Restaurant> restaurants = crudRestaurantRepository.findAll();
        List<Lunch> lunches = crudLunchRepository.getLunchByDateBetweenOrderByPriceDesc(startDate, endDate);
        return RestaurantTos(restaurants, lunches);
    }

    public void deleteById(int id) {
        checkNotFoundWithId(crudRestaurantRepository.deleteById(id) != 0, id);
    }

    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "meal must not be null");
        return crudRestaurantRepository.save(restaurant);
    }

    public Restaurant findById(int id) {
        return checkNotFoundWithId(crudRestaurantRepository.findById(id).orElse(null), id);
    }

    public Restaurant findByIdWithLunchs(int id) {
        return checkNotFoundWithId(crudRestaurantRepository.findByIdWithLunches(id), id);
    }

    private List<RestaurantTo> RestaurantTos(List<Restaurant> restaurants, List<Lunch> lunches) {
        Map<Integer, List<Lunch>> resIdMap = lunches.stream().collect(Collectors.groupingBy(lunch -> lunch.getRestaurant().getId()));

        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getName(), resIdMap.getOrDefault(restaurant.getId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }


}
