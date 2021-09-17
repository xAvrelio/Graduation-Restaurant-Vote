package com.restaurant.restaurantvote.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"menu"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lunchs", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"}, name = "lunchs_unique_name_menu_idx")})
public class Lunch extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 256)
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "real default 0")
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Menu menu;

    public Lunch(Integer id, String name, BigDecimal price) {
        super(id);
        this.name = name;
        this.price = price;
    }

}
