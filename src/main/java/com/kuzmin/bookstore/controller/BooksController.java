package com.kuzmin.bookstore.controller;

import com.kuzmin.bookstore.api.BooksApi;
import com.kuzmin.bookstore.api.model.Book;
import com.kuzmin.bookstore.hateoas.BookRepresentationModelAssembler;
import com.kuzmin.bookstore.service.BookService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class BooksController implements BooksApi {
    private final BookService bookService;
    private final BookRepresentationModelAssembler assembler;

    public BooksController(BookService bookService, BookRepresentationModelAssembler assembler) {
        this.bookService = bookService;
        this.assembler = assembler;
    }

    @Override
    public Mono<ResponseEntity<Flux<Book>>> queryBooks(
            @Valid @RequestParam(value = "id", required = false) String id,
            @Valid @RequestParam(value = "title", required = false) String title,
            @Valid @RequestParam(value = "ISBN", required = false) String ISBN,
            @Valid @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            final ServerWebExchange exchange) {
        return Mono.just(ok(assembler.toListModel(bookService.getAllBooks(), exchange)));
    }

    @Override
    public Mono<ResponseEntity<Book>> getBook(@PathVariable("id") Long id, final ServerWebExchange exchange) {
        return bookService.getBookById(id).map(c -> assembler.entityToModel(c, exchange))
                .map(model -> ResponseEntity.ok()
                        .cacheControl(CacheControl.maxAge(5, TimeUnit.DAYS))
                        .body(model)).defaultIfEmpty(notFound().build());
    }
}
