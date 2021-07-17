package com.restaurant.restaurantvote.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"lunches"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractBaseEntity {
    @Column(name = "name")
    @NotEmpty
    @Size(min = 2, max = 256)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Lunch> lunches;

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }


    public Restaurant(String name) {
        this.name = name;
    }

}
