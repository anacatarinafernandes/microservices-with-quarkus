package com.anafernandes.catalog.controller;

import com.anafernandes.catalog.model.Author;
import com.anafernandes.catalog.model.Availability;
import com.anafernandes.catalog.model.Book;
import com.anafernandes.catalog.model.Category;
import com.anafernandes.catalog.service.BookService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class BookControllerTest {

    @InjectMock
    BookService bookService;
    @Inject
    BookController bookController;
    private Book book;

    @BeforeEach
    void setUp() {

        var author = Author.builder()
                .name("Author1")
                .build();

        Category category = Category.builder()
                .name("Drama")
                .build();

        HashSet<Author> authors = new HashSet<>();
        authors.add(author);

        book = Book.builder()
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
    }

    @Test
    void getAllBooks() {

        List<Book> books = new ArrayList();
        books.add(book);
        Mockito.when(bookService.findAllBooks()).thenReturn(books);
        Response response = bookController.getAllBooks();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<Book> entity = (List<Book>) response.getEntity();
        assertFalse(entity.isEmpty());
        assertEquals("Book1", entity.get(0).getTitle());
        assertEquals(123456, entity.get(0).getIsbn());
    }

    @Test
    void getAllAvailableBooks() {

        List<Book> books = new ArrayList();
        books.add(book);
        Mockito.when(bookService.getAllAvailableBooks()).thenReturn(books);
        Response response = bookController.getAllAvailableBooks();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<Book> entity = (List<Book>) response.getEntity();
        assertFalse(entity.isEmpty());
        assertEquals("Book1", entity.get(0).getTitle());
        assertEquals(123456, entity.get(0).getIsbn());
    }

    @Test
    void addBook() {

        BookRequest bookRequest = BookRequest.builder()
                .title("Book1")
                .originalTitle("Book1")
                .language("Portuguese")
                .isbn(123456)
                .stockAvailable(2)
                .price(10.0)
                .dateCreated(LocalDateTime.now())
                .dateUpdated(LocalDateTime.now())
                .authors("Author1")
                .category("Category1")
                .availability("AVAILABLE")
                .build();

        Response response = bookController.addBook(bookRequest);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
        assertNull(response.getEntity());
    }

    @Test
    void filterBooks() {

        List<Book> books = new ArrayList();
        books.add(book);
        Mockito.when(bookService.filterBooks(any(String.class), any(String.class), any(Double.class))).thenReturn(books);
        Response response = bookController.filterBooks("Author1", "Category1", 10.0);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<Book> entity = (List<Book>) response.getEntity();
        assertFalse(entity.isEmpty());
        assertEquals("Book1", entity.get(0).getTitle());
        assertEquals(123456, entity.get(0).getIsbn());
    }


}