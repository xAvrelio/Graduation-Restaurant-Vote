package com.restaurant.restaurantvote.service;


import com.restaurant.restaurantvote.model.Menu;
import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.repository.MenuRepository;
import com.restaurant.restaurantvote.repository.RestaurantRepository;
import com.restaurant.restaurantvote.repository.VoteRepository;
import com.restaurant.restaurantvote.to.RestaurantTo;
import com.restaurant.restaurantvote.util.DateTimeUtil;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final VoteRepository voteRepository;

    private final MenuRepository menuRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, VoteRepository voteRepository, MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
        this.menuRepository = menuRepository;
    }


    @Transactional
    public void deleteById(int id) {
        checkNotFoundWithId(restaurantRepository.deleteById(id) != 0, id);
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public Restaurant findById(int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }


    public List<RestaurantTo> findAllWithMenuBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        startDate = Objects.isNull(startDate) ? DateTimeUtil.MIN_DATE : startDate;
        endDate = Objects.isNull(endDate) ? DateTimeUtil.MAX_DATE : endDate;
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) throw new NotFoundException("No Restaurants found");
        Map<Integer, List<Menu>> menuMap = menuRepository.findAllWithLunchsBetweenDatesToMap(startDate, endDate);

        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getName(), menuMap.getOrDefault(restaurant.getId(), Collections.EMPTY_LIST)))
                .collect(Collectors.toList());
    }

    public List<RestaurantTo> findAllWithMenuByDate(@Nullable LocalDate date) {
        date = Objects.isNull(date) ? LocalDate.now() : date;
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) throw new NotFoundException("No Restaurants found");
        Map<Integer, Menu> menuMap = menuRepository.findAllWithLunchsByDateToMap(date);

        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getName(), menuMap.getOrDefault(restaurant.getId(), null)))
                .collect(Collectors.toList());
    }


}
