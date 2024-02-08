package com.anafernandes.catalog.repository;

import com.anafernandes.catalog.model.Author;
import com.anafernandes.catalog.model.Availability;
import com.anafernandes.catalog.model.Book;
import com.anafernandes.catalog.model.Category;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@QuarkusTest
@Transactional
class BookRepositoryTest {
    @Inject
    BookRepository bookRepository;
    @Inject
    AuthorRepository authorRepository;
    @Inject
    CategoryRepository categoryRepository;

    private static Stream<Arguments> filterParams() {
        return Stream.of(
                arguments(1, null, null),
                arguments(null, 2, null),
                arguments(null, null, 20.0)
        );
    }

    @BeforeEach
    void setUp() {
        var author = Author.builder()
                .name("Author1")
                .build();

        authorRepository.persist(author);

        Category category = Category.builder()
                .name("Drama")
                .build();

        categoryRepository.persist(category);

        HashSet<Author> authors = new HashSet<>();
        authors.add(author);

        Book book = Book.builder()
                .title("Book1")
                .originalTitle("Book1")
                .language("Portuguese")
                .isbn(123456)
                .stockAvailable(2)
                .price(10.0)
                .dateCreated(LocalDateTime.now())
                .dateUpdated(LocalDateTime.now())
                .authors(authors)
                .category(category)
                .availability(Availability.AVAILABLE)
                .build();

        bookRepository.persist(book);

    }

    @AfterEach
    void tearDown() {

        bookRepository.deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void getAllBooksAvailable() {

        //when
        List<Book> bookResponse = bookRepository.getAllBooksAvailable();
        // then
        assertNotNull(bookResponse);
        assertFalse(bookResponse.isEmpty());
        assertEquals(bookResponse.getFirst().getTitle(), "Book1");
    }

    @Test
    void getBookByTitle() {

        //when
        Book bookResponse = bookRepository.getBookByTitle("Book1");
        // then
        assertNotNull(bookResponse);
        assertEquals(bookResponse.getTitle(), "Book1");
    }

    @ParameterizedTest
    @MethodSource("filterParams")
    void filterBooks(Integer authorId, Integer categoryId, Double maxPrice) {

        //when
        List<Book> bookResponse = bookRepository.filterBooks(authorId, categoryId, maxPrice);
        // then
        assertNotNull(bookResponse);
        assertFalse(bookResponse.isEmpty());

        assertEquals(bookResponse.getFirst().getTitle(), "Book1");
    }
}