package com.restaurant.restaurantvote.web;

import com.restaurant.restaurantvote.model.Menu;
import com.restaurant.restaurantvote.service.MenuService;
import com.restaurant.restaurantvote.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = MenuRestController.pathUrl, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    public static final String pathUrl = "/rest/admin/menus";

    private final MenuService service;

    @Autowired
    public MenuRestController(MenuService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable(name = "id") int id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menuId = {}", id);
        service.deleteById(id);
    }

    @GetMapping
    public List<Menu> findAll() {
        log.info("findAll");
        return service.findAllBetweenDates(null, null);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Menu menu, @PathVariable int id) {
        log.info("update entity = {} with id = {}", menu, id);
        ValidationUtil.assureIdConsistent(menu, id);
        service.update(menu, menu.getRestaurant().getId());
    }

    @PostMapping(value = "/restaurant/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@RequestBody Menu menu, @PathVariable(name = "id") int restaurantId) {
        log.info("create {} ", menu);
        ValidationUtil.checkNew(menu);
        Menu created = service.create(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(pathUrl + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/restaurant/{id}")
    public List<Menu> getByRestaurantId(@PathVariable(name = "id") int restaurantId) {
        log.info("get menus by restaurantId={}", restaurantId);
        return service.findAllByRestaurantId(restaurantId);
    }




}
