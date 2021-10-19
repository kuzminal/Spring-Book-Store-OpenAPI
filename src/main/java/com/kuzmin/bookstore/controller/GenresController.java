package com.kuzmin.bookstore.controller;

import com.kuzmin.bookstore.api.GenresApi;
import com.kuzmin.bookstore.api.model.Genre;
import com.kuzmin.bookstore.hateoas.GenreRepresentationModelAssembler;
import com.kuzmin.bookstore.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class GenresController implements GenresApi {
    private final GenreService genreService;
    private final GenreRepresentationModelAssembler genreRepresentationModelAssembler;

    public GenresController(GenreService genreService, GenreRepresentationModelAssembler genreRepresentationModelAssembler) {
        this.genreService = genreService;
        this.genreRepresentationModelAssembler = genreRepresentationModelAssembler;
    }

    @Override
    public Mono<ResponseEntity<Flux<Genre>>> getAllGenres(final ServerWebExchange exchange) {
        return Mono.just(ok(genreRepresentationModelAssembler.toListModel(genreService.getAllGenres(), exchange)));
    }

    @Override
    public Mono<ResponseEntity<Genre>> getGenreById(@PathVariable("id") Long id, final ServerWebExchange exchange) {
        return genreService.getGenreById(id).map(c -> genreRepresentationModelAssembler.entityToModel(c, exchange))
                .map(ResponseEntity::ok).defaultIfEmpty(notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteGenreById(@PathVariable("id") Long id, final ServerWebExchange exchange) {
        return genreService.getGenreById(id)
                .flatMap(a -> genreService.deleteById(a.getId()))
                .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build()))
                .switchIfEmpty(Mono.just(notFound().build()));
    }
}
