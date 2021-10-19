package com.kuzmin.bookstore.hateoas;

import com.kuzmin.bookstore.api.model.Genre;
import com.kuzmin.bookstore.controller.GenresController;
import com.kuzmin.bookstore.model.GenreEntity;
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
public class GenreRepresentationModelAssembler implements ReactiveRepresentationModelAssembler<GenreEntity, Genre>, HateoasSupport {
    private static String serverUri = null;
    @Override
    public Mono<Genre> toModel(GenreEntity entity, ServerWebExchange exchange) {
        Genre resource = new Genre();
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId());
        resource.add(
                linkTo(methodOn(GenresController.class).getGenreById(entity.getId(), exchange))
                        .withSelfRel());
        return Mono.just(resource);
    }

    public Flux<Genre> toListModel(Flux<GenreEntity> entities, ServerWebExchange exchange) {
        if (Objects.isNull(entities)) {
            return Flux.empty();
        }
        return Flux.from(entities.map(e -> entityToModel(e, exchange)));
    }

    public Genre entityToModel(GenreEntity entity, ServerWebExchange exchange) {
        Genre resource = new Genre();
        if(Objects.isNull(entity)) {
            return resource;
        }
        BeanUtils.copyProperties(entity, resource);
        resource.id(entity.getId())
                .setName(entity.getName());
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
