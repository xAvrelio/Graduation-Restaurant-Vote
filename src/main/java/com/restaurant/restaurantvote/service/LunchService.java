package com.restaurant.restaurantvote.service;

import com.restaurant.restaurantvote.model.Lunch;
import com.restaurant.restaurantvote.repository.LunchRepository;
import com.restaurant.restaurantvote.repository.MenuRepository;
import com.restaurant.restaurantvote.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.restaurant.restaurantvote.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class LunchService {

    private final LunchRepository lunchRepository;

    private final MenuRepository menuRepository;

    public LunchService(LunchRepository lunchRepository, MenuRepository menuRepository) {
        this.lunchRepository = lunchRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public void deleteById(int id) {
        checkNotFoundWithId(lunchRepository.deleteById(id) != 0, id);
    }


    public Lunch save(Lunch lunch, int menuId) {
        Assert.notNull(lunch, "lunch must not be null");
        if (!menuRepository.existsById(menuId)) {
            throw new NotFoundException("menu not found id=" + menuId);
        }
        lunch.setMenu(menuRepository.getById(menuId));
        return checkNotFoundWithId(lunchRepository.save(lunch), lunch.id());
    }

    public Lunch findById(int id) {
        return checkNotFoundWithId(lunchRepository.findById(id).orElse(null), id);
    }

    public List<Lunch> findAll (){
        return lunchRepository.findAll();
    }

    public List<Lunch> findByMenuId(int menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new NotFoundException("menu not found id=" + menuId);
        }
        return lunchRepository.findAllByMenu_Id(menuId);
    }



}
