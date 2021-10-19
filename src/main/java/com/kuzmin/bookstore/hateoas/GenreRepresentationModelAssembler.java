package com.kuzmin.bookstore.hateoas;

import com.kuzmin.bookstore.api.model.Genre;
import com.kuzmin.bookstore.controller.GenresController;
import com.kuzmin.bookstore.model.GenreEntity;
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
public class GenreRepresentationModelAssembler extends RepresentationModelAssemblerSupport<GenreEntity, Genre> {
    public GenreRepresentationModelAssembler() {
        super(GenresController.class, Genre.class);
    }

    @Override
    public Genre toModel(GenreEntity entity) {
        Genre resource = new Genre();
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId());
        resource.add(
                linkTo(methodOn(GenresController.class).getGenreById(entity.getId()))
                        .withSelfRel());
        return resource;
    }

    public List<Genre> toListModel(Iterable<GenreEntity> entities) {
        if (Objects.isNull(entities)) return Collections.emptyList();
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(toList());
    }
}
