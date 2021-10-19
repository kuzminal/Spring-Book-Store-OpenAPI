package com.kuzmin.bookstore.controller;

import com.kuzmin.bookstore.api.AuthorsApi;
import com.kuzmin.bookstore.api.model.Author;
import com.kuzmin.bookstore.hateoas.AuthorRepresentationModelAssembler;
import com.kuzmin.bookstore.service.AuthorsService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Controller
public class AuthorsController implements AuthorsApi {
    private final AuthorsService authorsService;
    private final AuthorRepresentationModelAssembler authorRepresentationModelAssembler;

    public AuthorsController(AuthorsService authorsService, AuthorRepresentationModelAssembler authorRepresentationModelAssembler) {
        this.authorsService = authorsService;
        this.authorRepresentationModelAssembler = authorRepresentationModelAssembler;
    }

    @Override
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ok(authorRepresentationModelAssembler.toListModel(authorsService.getAllAuthors()));
    }

    @Override
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
        return authorsService.getAuthorById(id)
                .map(authorRepresentationModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteAuthorById(@PathVariable("id") Long id) {
        if (authorsService.getAuthorById(id).isPresent()) {
            authorsService.deleteAuthorById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
