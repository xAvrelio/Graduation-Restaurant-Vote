package com.restaurant.restaurantvote.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_name_idx")})
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name")
    @NotBlank
    @Size(min = 2, max = 256)
    private String name;

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Restaurant(String name) {
        this.name = name;
    }

}
