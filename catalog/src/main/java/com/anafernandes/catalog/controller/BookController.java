package com.anafernandes.catalog.controller;

import com.anafernandes.catalog.exception.BookNotFoundException;
import com.anafernandes.catalog.model.Author;
import com.anafernandes.catalog.model.Book;
import com.anafernandes.catalog.service.BookService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/api/book")
public class BookController {

    @Inject
    BookService bookService;

    @GET
    public Response getAllBooks() {

        List<Book> books = new ArrayList<>();

        books = bookService.findAllBooks();


        return Response.ok(books).build();
    }

    @GET
    @Path("/available")
    public Response getAllAvailableBooks() {

        List<Book> books = new ArrayList<>();

        books = bookService.getAllAvailableBooks();


        return Response.ok(books).build();
    }


    @POST
    public Response addBook(BookRequest book) {

        bookService.addBook(book);

        return Response.created(URI.create("/book")).build();
    }

    @GET
    @Path("/{bookTitle}")
    public Response getBookByTitle(@PathParam("bookTitle") String bookTitle) throws BookNotFoundException {

        Book book = bookService.GetBookDetails(bookTitle);

        if (book != null) {

            return Response.ok(book).build();
        }
        throw new BookNotFoundException("Book with title " + bookTitle + " not found");
    }

    @GET
    @Path("/filter")
    public Response filterBooks(
            @QueryParam("author") String author,
            @QueryParam("category") String category,
            @QueryParam("maxPrice") Double maxPrice) {

        List<Book> books = bookService.filterBooks(author, category, maxPrice);

        return Response.ok(books).build();

    }

    @POST
    @Path("/author")
    public void addAuthor(Author author) {

        bookService.addAuthor(author);
    }

    @POST
    @Path("/category")
    public void addCategory(String name) {

        bookService.addCategory(name);
    }

}
