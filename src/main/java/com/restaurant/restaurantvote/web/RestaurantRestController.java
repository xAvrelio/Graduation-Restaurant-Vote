package com.restaurant.restaurantvote.web;


import com.restaurant.restaurantvote.model.Restaurant;
import com.restaurant.restaurantvote.service.RestaurantService;
import com.restaurant.restaurantvote.to.RestaurantTo;
import com.restaurant.restaurantvote.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = RestaurantRestController.pathUrl, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    public static final String pathUrl = "/rest/restaurants";

    private final RestaurantService service;

    @Autowired
    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get by restaurantId = {}", id);
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurantId = {}", id);
        service.deleteById(id);
    }

    @GetMapping
    public List<Restaurant> findAll() {
        log.info("findAll");
        return service.findAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update entity = {} with id = {}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create {} ", restaurant);
        ValidationUtil.checkNew(restaurant);
        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(pathUrl + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/filter-with-menu")
    public List<RestaurantTo> findBetweenDatesWithMenu(
            @RequestParam(required = false) @Nullable LocalDate startDate,
            @RequestParam(required = false) @Nullable LocalDate endDate) {
        log.info("find all with menu between startDate = {}, endDate = {}", startDate, endDate);
        return service.findAllWithMenuBetweenDates(startDate, endDate);
    }

    @GetMapping("/with-menu")
    public List<RestaurantTo> findTodayWithMenu() {
        LocalDate localDate = LocalDate.now();
        log.info("find all with menu today = {}", localDate);
        return service.findAllWithMenuByDate(localDate);
    }


}
