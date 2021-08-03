package com.restaurant.restaurantvote.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true, exclude = {"restaurant", "lunchs"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "create_date"}, name = "menus_unique_date_restaurant_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "create_date")
    @NotNull
    private LocalDate createDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    private List<Lunch> lunchs =  new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Menu(Integer id, LocalDate createDate) {
        super(id);
        this.createDate = createDate;
    }
}
