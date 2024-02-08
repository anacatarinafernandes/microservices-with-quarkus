package com.anafernandes.catalog.repository;

import com.anafernandes.catalog.model.Availability;
import com.anafernandes.catalog.model.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    public List<Book> getAllBooksAvailable() {
        return list("availability", Availability.AVAILABLE);

    }

    public List<Book> filterBooks(Integer authorId, Integer categoryId, Double maxPrice) {


        List<Book> originalList = Book.findAll().list();

        if (authorId != null) {

            originalList = originalList.stream()
                    .filter(pr -> pr.getAuthors().stream().anyMatch(s -> authorId.equals(s.getId())))
                    .collect(Collectors.toList());

        }
        if (categoryId != null) {

            originalList = originalList.stream()
                    .filter(pr -> pr.getCategory().getId().equals(categoryId))
                    .collect(Collectors.toList());

        }

        if (maxPrice != null) {

            originalList = originalList.stream()
                    .filter(pr -> pr.getPrice() < maxPrice)
                    .collect(Collectors.toList());
        }


        return originalList;


        //price
        //return find("price < :maxPrice", Parameters.with("maxPrice", maxPrice)).list();
        //category
        //return find("category.id = :categoryId", Parameters.with("categoryId", categoryId)).list();
        //author(not working)
        //return find("authors[0].id = :authorId", Parameters.with("authorId", authorId)).list();


        //PanacheQuery<Book> query = Book.find(eq(s -> {s.authors = :authorId; s.lastName = "Mathieu";}));

        //return find("authorId = :authorId", Parameters.with("authorId", authorId)).list();

        //return find("Select m from Book m Join m.author p where p.id=?1", authorId).list();
        //return find("date > :date", Parameters.with("date", date)).list();
        // return listAll(Sort.by("SELECT b FROM Book b JOIN b.authors a WHERE (:authorId is null or a.id= :authorId )AND (:categoryId is null OR b.category.Id = :categoryId) AND (:maxPrice is null OR b.price <= :maxPrice)"));

    }


    public Book getBookByTitle(String bookTitle) {

        return find("title", bookTitle).firstResult();
    }
}
