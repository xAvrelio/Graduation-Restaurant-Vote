package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.repository.LunchRepository;
import com.restaurant.restaurantvote.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class LunchService {

    private LunchRepository lunchRepository;

    private RestaurantRepository restaurantRepository;

    public LunchService(LunchRepository lunchRepository, RestaurantRepository restaurantRepository) {
        this.lunchRepository = lunchRepository;
        this.restaurantRepository = restaurantRepository;
    }


    public void deleteById(int id) {
        checkNotFoundWithId(lunchRepository.deleteById(id) != 0, id);
    }


    public Lunch save(Lunch lunch, int restaurantId) {
        Assert.notNull(lunch, "meal must not be null");
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (!lunch.isNew() && restaurant == null) {
            return null;
        }
        lunch.setRestaurant(restaurant);
        return checkNotFoundWithId(lunchRepository.save(lunch), lunch.id());
    }

    public Lunch findById(int id) {
        return checkNotFoundWithId(lunchRepository.findById(id).orElse(null), id);
    }

    public List<Lunch> findByDate(LocalDate date) {
        return lunchRepository.findLunchByDate(date);
    }

    public List<Lunch> findBetweenDates(LocalDate startDate, LocalDate endDate) {
        return lunchRepository.findLunchByDateBetween(startDate, endDate);
    }


}
