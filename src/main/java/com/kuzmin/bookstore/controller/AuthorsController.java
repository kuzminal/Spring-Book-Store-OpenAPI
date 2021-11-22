package com.kuzmin.bookstore.controller;

import com.kuzmin.bookstore.api.AuthorsApi;
import com.kuzmin.bookstore.api.model.Author;
import com.kuzmin.bookstore.hateoas.AuthorRepresentationModelAssembler;
import com.kuzmin.bookstore.service.AuthorsService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class AuthorsController implements AuthorsApi {
    private final AuthorsService authorsService;
    private final AuthorRepresentationModelAssembler authorRepresentationModelAssembler;

    public AuthorsController(AuthorsService authorsService, AuthorRepresentationModelAssembler authorRepresentationModelAssembler) {
        this.authorsService = authorsService;
        this.authorRepresentationModelAssembler = authorRepresentationModelAssembler;
    }

    @Override
    public Mono<ResponseEntity<Flux<Author>>> getAllAuthors(final ServerWebExchange exchange) {
        return Mono.just(ok(authorRepresentationModelAssembler.toListModel(authorsService.getAllAuthors(), exchange)));
    }

    @Override
    public Mono<ResponseEntity<Author>> getAuthorById(@PathVariable("id") Long id, final ServerWebExchange exchange) {
        return authorsService.getAuthorById(id).map(c -> authorRepresentationModelAssembler.entityToModel(c, exchange))
                .map( ent ->
                        //ResponseEntity::ok
                        ResponseEntity
                                .ok()
                                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                                .eTag(ent.getVersion().toString()) // lastModified is also available
                                .body(ent)
                ).defaultIfEmpty(notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAuthorById(@PathVariable("id") Long id, final ServerWebExchange exchange) {
        return authorsService.getAuthorById(id)
                .flatMap(a -> authorsService.deleteAuthorById(a.getId()))
                .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build()))
                .switchIfEmpty(Mono.just(notFound().build()));
    }
}
