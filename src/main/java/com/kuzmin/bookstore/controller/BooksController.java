package com.kuzmin.bookstore.controller;

import com.kuzmin.bookstore.api.BooksApi;
import com.kuzmin.bookstore.api.model.Book;
import com.kuzmin.bookstore.hateoas.BookRepresentationModelAssembler;
import com.kuzmin.bookstore.service.AuthorsService;
import com.kuzmin.bookstore.service.BookService;
import com.kuzmin.bookstore.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Controller
public class BooksController implements BooksApi {
    private final BookService bookService;
    private final BookRepresentationModelAssembler assembler;

    public BooksController(BookService bookService, BookRepresentationModelAssembler assembler, AuthorsService authorsService, GenreService genreService) {
        this.bookService = bookService;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<List<Book>> queryBooks(
            @Valid @RequestParam(value = "id", required = false) String id,
            @Valid @RequestParam(value = "title", required = false) String title,
            @Valid @RequestParam(value = "ISBN", required = false) String ISBN,
            @Valid @RequestParam(value = "page", required = false, defaultValue="1") Integer page,
            @Valid @RequestParam(value = "size", required = false, defaultValue="10") Integer size) {
        return ok(assembler.toListModel(bookService.getAllBooks()));
    }

    @Override
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        return bookService.getBookById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }
}
