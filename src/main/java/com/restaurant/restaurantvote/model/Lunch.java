package com.restaurant.restaurantvote.model;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"restaurant"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lunch", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date", "name"}, name = "lunch_unique_restaurant_date_name_idx")})
public class Lunch extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 256)
    private String name;
    @Column(name = "price", nullable = false, columnDefinition = "real default 0")
    @NotNull
    private BigDecimal price;
    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Lunch(Integer id, String name, BigDecimal price, LocalDate date) {
        super(id);
        this.name = name;
        this.price = price;
        this.date = date;
    }

}
