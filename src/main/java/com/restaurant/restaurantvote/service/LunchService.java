package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.repository.CrudLunchRepository;
import com.restaurant.restaurantvote.repository.CrudRestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
public class LunchService {

    private CrudLunchRepository crudLunchRepository;

    private CrudRestaurantRepository crudRestaurantRepository;

    public LunchService(CrudLunchRepository crudLunchRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudLunchRepository = crudLunchRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }


    public void delete(int id) {
        checkNotFoundWithId(crudLunchRepository.deleteById(id) != 0, id);
    }


    public Lunch save(Lunch lunch, int restaurantId) {
        Assert.notNull(lunch, "meal must not be null");
        Restaurant restaurant = crudRestaurantRepository.findById(restaurantId).orElse(null);
        if (!lunch.isNew() && restaurant == null) {
            return null;
        }
        lunch.setRestaurant(restaurant);
        return checkNotFoundWithId(crudLunchRepository.save(lunch), lunch.id());
    }

    public Lunch findById(int id) {
        return checkNotFoundWithId(crudLunchRepository.findById(id).orElse(null), id);
    }

    public List<Lunch> findByDate(LocalDate date) {
        return crudLunchRepository.getLunchByDateOrderByPriceDesc(date);
    }

    public List<Lunch> findBetweenDates(LocalDate startDate, LocalDate endDate) {
        return crudLunchRepository.getLunchByDateBetweenOrderByPriceDesc(startDate, endDate);
    }



}
