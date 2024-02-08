package com.anafernandes.catalog.repository;

import com.anafernandes.catalog.model.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorRepository  implements PanacheRepository<Author> {

    public Author getAuthorByName(String name) {
        return find("name", name).firstResult();
    }
}
