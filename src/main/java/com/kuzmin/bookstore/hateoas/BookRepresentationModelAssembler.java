package com.kuzmin.bookstore.hateoas;

import com.kuzmin.bookstore.api.model.Book;
import com.kuzmin.bookstore.controller.BooksController;
import com.kuzmin.bookstore.model.BookEntity;
import com.kuzmin.bookstore.service.AuthorsService;
import com.kuzmin.bookstore.service.GenreService;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookRepresentationModelAssembler extends RepresentationModelAssemblerSupport<BookEntity, Book> {
    private final GenreRepresentationModelAssembler genreRepresentationModelAssembler;
    private final AuthorRepresentationModelAssembler authorRepresentationModelAssembler;

    public BookRepresentationModelAssembler(AuthorsService authorsService, GenreService genreService, GenreRepresentationModelAssembler genreRepresentationModelAssembler, AuthorRepresentationModelAssembler authorRepresentationModelAssembler) {
        super(BooksController.class, Book.class);
        this.genreRepresentationModelAssembler = genreRepresentationModelAssembler;
        this.authorRepresentationModelAssembler = authorRepresentationModelAssembler;
    }

    @Override
    public Book toModel(BookEntity entity) {
        Book resource = new Book();
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId());
        resource.setAuthors(authorRepresentationModelAssembler.toListModel(entity.getAuthors()));
        resource.setGenre(genreRepresentationModelAssembler.toModel(entity.getGenre()));
        resource.add(
                linkTo(methodOn(BooksController.class).getBook(entity.getId()))
                        .withSelfRel());
        return resource;
    }

    public List<Book> toListModel(Iterable<BookEntity> entities) {
        if (Objects.isNull(entities)) return Collections.emptyList();
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(toList());
    }
}
