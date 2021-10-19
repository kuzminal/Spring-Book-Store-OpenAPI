package com.kuzmin.bookstore.hateoas;

import com.kuzmin.bookstore.api.model.Author;
import com.kuzmin.bookstore.controller.AuthorsController;
import com.kuzmin.bookstore.model.AuthorsEntity;
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
public class AuthorRepresentationModelAssembler extends RepresentationModelAssemblerSupport<AuthorsEntity, Author> {
    public AuthorRepresentationModelAssembler() {
        super(AuthorsController.class, Author.class);
    }

    @Override
    public Author toModel(AuthorsEntity entity) {
        Author resource = new Author();
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId());
        resource.add(
                linkTo(methodOn(AuthorsController.class).getAuthorById(entity.getId()))
                        .withSelfRel());
        return resource;
    }

    public List<Author> toListModel(Iterable<AuthorsEntity> entities) {
        if (Objects.isNull(entities)) return Collections.emptyList();
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).collect(toList());
    }
}
