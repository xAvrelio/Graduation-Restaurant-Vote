package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.repository.LunchRepository;
import com.restaurant.restaurantvote.repository.RestaurantRepository;
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

    private LunchRepository lunchRepository;

    private RestaurantRepository restaurantRepository;

    public RestaurantService(LunchRepository lunchRepository, RestaurantRepository restaurantRepository) {
        this.lunchRepository = lunchRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantTo> getWithLunchesByDate(LocalDate date) {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Lunch> lunches = lunchRepository.findLunchByDate(date);
        return RestaurantTos(restaurants, lunches);
    }

    public List<RestaurantTo> getWithLunchesBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Lunch> lunches = lunchRepository.findLunchByDateBetween(startDate, endDate);
        return RestaurantTos(restaurants, lunches);
    }

    public void deleteById(int id) {
        checkNotFoundWithId(restaurantRepository.deleteById(id) != 0, id);
    }

    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "meal must not be null");
        return restaurantRepository.save(restaurant);
    }

    public Restaurant findById(int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }

    public Restaurant findByIdWithLunchs(int id) {
        return checkNotFoundWithId(restaurantRepository.findByIdWithLunches(id), id);
    }

    public List<Restaurant> findAllWithLunches() {
        return restaurantRepository.findByIdWithLunches();
    }

    private List<RestaurantTo> RestaurantTos(List<Restaurant> restaurants, List<Lunch> lunches) {
        Map<Integer, List<Lunch>> resIdMap = lunches.stream().collect(Collectors.groupingBy(lunch -> lunch.getRestaurant().getId()));

        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getName(), resIdMap.getOrDefault(restaurant.getId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }

}
