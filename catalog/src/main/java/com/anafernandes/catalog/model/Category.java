package com.anafernandes.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category  {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Book> books;
}
