package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Menu;
import com.restaurant.restaurantvote.repository.MenuRepository;
import com.restaurant.restaurantvote.repository.RestaurantRepository;
import com.restaurant.restaurantvote.util.DateTimeUtil;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;


    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public void deleteById(int id) {
        checkNotFoundWithId(menuRepository.deleteById(id) != 0, id);
    }

    @Transactional
    @Modifying
    public void update(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new NotFoundException("Not found restaurantId =" + restaurantId);
        }
        menu.setRestaurant(restaurantRepository.getById(restaurantId));
        checkNotFoundWithId(menuRepository.save(menu), menu.id());
    }

    @Transactional
    @Modifying
    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new NotFoundException("Not found restaurantId =" + restaurantId);
        }
        menu.setRestaurant(restaurantRepository.getById(restaurantId));
        return checkNotFoundWithId(menuRepository.save(menu), menu.id());
    }

    public Menu findById(int id) {
        return checkNotFoundWithId(menuRepository.findById(id).orElse(null), id);
    }

    public List<Menu> findAllByRestaurantId(int restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new NotFoundException("Not found restaurantId =" + restaurantId);
        }
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    public List<Menu> findAllByDateWithLunches(@Nullable LocalDate date) {
        date = Objects.isNull(date) ? LocalDate.now() : date;
        return menuRepository.findAllWithLunchsByDate(date);
    }

    public List<Menu> findAllBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate){
        startDate = Objects.isNull(startDate) ? DateTimeUtil.MIN_DATE : startDate;
        endDate = Objects.isNull(endDate) ? DateTimeUtil.MAX_DATE : endDate;
        return menuRepository.findAllWithLunchsBetweenDates(startDate, endDate);
    }

}
