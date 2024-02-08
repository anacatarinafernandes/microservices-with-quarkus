package com.anafernandes.catalog.service;

import com.anafernandes.catalog.controller.BookRequest;
import com.anafernandes.catalog.model.Author;
import com.anafernandes.catalog.model.Availability;
import com.anafernandes.catalog.model.Book;
import com.anafernandes.catalog.model.Category;
import com.anafernandes.catalog.repository.AuthorRepository;
import com.anafernandes.catalog.repository.BookRepository;
import com.anafernandes.catalog.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@AllArgsConstructor
@Transactional
public class BookService {

    @Inject
    BookRepository bookRepository;

    @Inject
    AuthorRepository authorRepository;

    @Inject
    CategoryRepository categoryRepository;


    public List<Book> findAllBooks() {

        return bookRepository.findAll().list();
    }

    public List<Book> getAllAvailableBooks() {

        return bookRepository.getAllBooksAvailable();
    }


    public void addBook(BookRequest bookRequest) {

        Author author = authorRepository.getAuthorByName(bookRequest.getAuthors());

        Set<Author> authors = new HashSet<>();
        authors.add(author);

        Category category = categoryRepository.getCategoryByName(bookRequest.getCategory());

        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .originalTitle(bookRequest.getOriginalTitle())
                .language(bookRequest.getLanguage())
                .isbn(bookRequest.getIsbn())
                .stockAvailable(bookRequest.getStockAvailable())
                .price(bookRequest.getPrice())
                .dateCreated(LocalDateTime.now())
                .dateUpdated(LocalDateTime.now())
                .build();

        book.setAuthors(authors);
        book.setCategory(category);
        book.setAvailability(Availability.valueOf(bookRequest.getAvailability()));


        author.getBooks().add(book);
        authorRepository.persist(author);
        bookRepository.persist(book);

    }

    public List<Book> filterBooks(String authorName, String categoryName, Double maxPrice) {


        Integer categoryId = null;

        Integer bookId = null;

        if (categoryName != null) {

            categoryId = categoryRepository.getCategoryByName(categoryName).getId();
        }

        if (authorName != null) {

            bookId = authorRepository.getAuthorByName(authorName).getId();

        }


        return bookRepository.filterBooks(bookId, categoryId, maxPrice);
    }

    public void addAuthor(Author author) {

        authorRepository.persist(author);
    }


    public void addCategory(String name) {

        Category category = Category.builder().name(name).build();
        categoryRepository.persist(category);
    }

    public Book GetBookDetails(String bookTitle) {

        return bookRepository.getBookByTitle(bookTitle);
    }
}
