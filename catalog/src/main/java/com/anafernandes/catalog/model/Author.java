package com.anafernandes.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String fullName;
    private LocalDate birthDate;
    private String birthPlace;
    private LocalDate deathDate;
    private String deathPlace;
    private String about;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    Set<Book> books = new HashSet<>();
}
