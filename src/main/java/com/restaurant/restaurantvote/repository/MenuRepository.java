package com.restaurant.restaurantvote.repository;

import com.restaurant.restaurantvote.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Query("DELETE FROM Menu m where m.id=:id")
    int deleteById(int id);

    @EntityGraph(attributePaths = {"lunchs"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.createDate >= :startDate and m.createDate <= :endDate")
    List<Menu> findAllWithLunchsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @EntityGraph(attributePaths = {"lunchs"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.createDate=:date")
    List<Menu> findAllWithLunchsByDate(LocalDate date);

    List<Menu> findAllByRestaurant_Id(int restaurantId);

    //https://stackoverflow.com/questions/41876122/spring-data-jpa-repository-findall-to-return-map-instead-of-list
    //Granted by DB it's will return a unique menu for restaurant in 1 day
    default Map<Integer, Menu> findAllWithLunchsByDateToMap(LocalDate date) {
        return findAllWithLunchsByDate(date).stream().collect(Collectors.toMap(menu -> menu.getRestaurant().getId(), v -> v));
    }

    default Map<Integer, List<Menu>> findAllWithLunchsBetweenDatesToMap(LocalDate startDate, LocalDate endDate) {
        return findAllWithLunchsBetweenDates(startDate, endDate).stream().collect(Collectors.groupingBy(menu -> menu.getRestaurant().getId()));
    }
}
