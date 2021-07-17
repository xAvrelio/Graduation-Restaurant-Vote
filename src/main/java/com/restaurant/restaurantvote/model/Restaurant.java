package com.restaurant.restaurantvote.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"lunches"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractBaseEntity {

    public Restaurant (String name) {
        this.name = name;
    }

    @Column(name = "name")
    @NotEmpty
    @Size(min = 2 , max = 256)
    private String name;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Lunch> lunches;

}
