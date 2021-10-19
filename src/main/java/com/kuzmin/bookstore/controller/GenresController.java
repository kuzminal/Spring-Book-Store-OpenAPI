package com.kuzmin.bookstore.controller;

import com.kuzmin.bookstore.api.GenresApi;
import com.kuzmin.bookstore.api.model.Genre;
import com.kuzmin.bookstore.hateoas.GenreRepresentationModelAssembler;
import com.kuzmin.bookstore.service.GenreService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Controller
public class GenresController implements GenresApi {
    private final GenreService genreService;
    private final GenreRepresentationModelAssembler genreRepresentationModelAssembler;

    public GenresController(GenreService genreService, GenreRepresentationModelAssembler genreRepresentationModelAssembler) {
        this.genreService = genreService;
        this.genreRepresentationModelAssembler = genreRepresentationModelAssembler;
    }

    @Override
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ok(genreRepresentationModelAssembler.toListModel(genreService.getAllGenres()));

    }

    @Override
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id)
                .map(genreRepresentationModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteGenreById(@PathVariable("id") Long id) {
        if (genreService.getGenreById(id).isPresent()) {
            genreService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
