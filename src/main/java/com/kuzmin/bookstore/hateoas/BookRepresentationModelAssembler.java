package com.kuzmin.bookstore.hateoas;

import com.kuzmin.bookstore.api.model.Book;
import com.kuzmin.bookstore.controller.BooksController;
import com.kuzmin.bookstore.model.BookEntity;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookRepresentationModelAssembler implements ReactiveRepresentationModelAssembler<BookEntity, Book>, HateoasSupport {
    private static String serverUri = null;

    @Override
    public Mono<Book> toModel(BookEntity entity, ServerWebExchange exchange) {
        Book resource = new Book();
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId());
        resource.add(
                linkTo(methodOn(BooksController.class).getBook(entity.getId(), exchange))
                        .withSelfRel());
        return Mono.just(resource);
    }

    public Flux<Book> toListModel(Flux<BookEntity> entities, ServerWebExchange exchange) {
        if (Objects.isNull(entities)) {
            return Flux.empty();
        }
        return Flux.from(entities.map(e -> entityToModel(e, exchange)));
    }

    public Book entityToModel(BookEntity entity, ServerWebExchange exchange) {
        Book resource = new Book();
        if(Objects.isNull(entity)) {
            return resource;
        }
        BeanUtils.copyProperties(entity, resource);
        resource.id(entity.getId())
                .annotation(entity.getAnnotation())
                .count(entity.getCount())
                .imageCover(entity.getImageCover())
                .isbn(entity.getIsbn())
                .price(entity.getPrice())
                //.authors(authorRepresentationModelAssembler.toListModel(Flux.fromIterable(entity.getAuthors()), exchange))
                //.genre(entity.getGenre())
                .title(entity.getTitle());
        String serverUri = getServerUri(exchange);
        resource.add(Link.of(String.format("%s/api/v1/orders", serverUri)).withRel("orders"));
        resource.add(
                Link.of(String.format("%s/api/v1/Orders/%s", serverUri, entity.getId())).withSelfRel());
        return resource;
    }

    private String getServerUri(@Nullable ServerWebExchange exchange) {
        if (Strings.isBlank(serverUri)) {
            serverUri = getUriComponentBuilder(exchange).toUriString();
        }
        return serverUri;
    }
}
