package com.anafernandes.catalog.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String originalTitle;
    private Integer isbn;
    private String language;
    private Integer stockAvailable;
    private Double price;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "bookAuthors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    Set<Author> authors;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    Category category;

    private Availability availability;

}
