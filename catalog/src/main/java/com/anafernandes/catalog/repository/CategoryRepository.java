package com.anafernandes.catalog.repository;

import com.anafernandes.catalog.model.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {

    public Category getCategoryByName(String name) {

        return find("name", name).firstResult();
    }

}
