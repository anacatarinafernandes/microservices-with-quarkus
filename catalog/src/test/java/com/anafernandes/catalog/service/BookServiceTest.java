package com.anafernandes.catalog.service;

import com.anafernandes.catalog.controller.BookRequest;
import com.anafernandes.catalog.model.Author;
import com.anafernandes.catalog.model.Book;
import com.anafernandes.catalog.model.Category;
import com.anafernandes.catalog.repository.AuthorRepository;
import com.anafernandes.catalog.repository.BookRepository;
import com.anafernandes.catalog.repository.CategoryRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class BookServiceTest {

    @InjectMock
    private BookRepository bookRepository;
    @InjectMock
    private AuthorRepository authorRepository;
    @InjectMock
    private CategoryRepository categoryRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(
                bookRepository,
                authorRepository,
                categoryRepository);
    }

    @Test
    void findAllBooks() {
        PanacheQuery query = Mockito.mock(PanacheQuery.class);
//        when(query.page(Mockito.any())).thenReturn(query);
//        when(query.list()).thenReturn(new ArrayList<>());
        when(bookRepository.findAll()).thenReturn(query);

        //when
        bookService.findAllBooks();
        //then
        verify(bookRepository).findAll();
    }


    @Test
    void getAllAvailableBooks() {

        when(bookRepository.getAllBooksAvailable()).thenReturn(new ArrayList<>());

        //when
        bookService.getAllAvailableBooks();
        //then
        verify(bookRepository).getAllBooksAvailable();
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

        var author = Author.builder()
                .name("Author1")
                .books(new HashSet<>())
                .build();

        when(authorRepository.getAuthorByName(any(String.class))).thenReturn(author);


        //when
        bookService.addBook(bookRequest);
        //then
        verify(categoryRepository).getCategoryByName(any(String.class));
        verify(authorRepository).getAuthorByName(any(String.class));
        verify(bookRepository).persist(any(Book.class));

    }

    @Test
    void filterBooksAllParameters() {
        //given
        String authorName = "Author1";
        String categoryName = "Category1";
        Double maxPrice = 10.0;

        var category = Category.builder()
                .name("Category1")
                .id(1)
                .build();

        when(categoryRepository.getCategoryByName(any(String.class))).thenReturn(category);

        var author = Author.builder()
                .name("Author1")
                .id(1)
                .build();

        when(authorRepository.getAuthorByName(any(String.class))).thenReturn(author);

        bookService.filterBooks(authorName, categoryName, maxPrice);


        verify(bookRepository).filterBooks(author.getId(), category.getId(), maxPrice);

    }

    @Test
    void filterBooksWithNullParameters() {
        //given
        String authorName = null;
        String categoryName = null;
        Double maxPrice = null;

        bookService.filterBooks(authorName, categoryName, maxPrice);

        verify(bookRepository).filterBooks(null, null, null);
    }

    @Test
    void addAuthor() {

        //given
        var author = Author.builder()
                .name("Author1")
                .build();

        //when
        bookService.addAuthor(author);
        //then
        verify(authorRepository).persist(any(Author.class));
    }

    @Test
    void addCategory() {

        bookService.addCategory("Category1");

        verify(categoryRepository).persist(any(Category.class));

    }
}