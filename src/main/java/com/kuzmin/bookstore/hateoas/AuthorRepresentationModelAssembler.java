package com.kuzmin.bookstore.hateoas;

import com.kuzmin.bookstore.api.model.Author;
import com.kuzmin.bookstore.controller.AuthorsController;
import com.kuzmin.bookstore.model.AuthorsEntity;
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
public class AuthorRepresentationModelAssembler implements ReactiveRepresentationModelAssembler<AuthorsEntity, Author>, HateoasSupport {
    private static String serverUri = null;
    @Override
    public Mono<Author> toModel(AuthorsEntity entity, ServerWebExchange exchange) {
        Author resource = new Author();
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId());
        resource.add(
                linkTo(methodOn(AuthorsController.class).getAuthorById(entity.getId(), exchange))
                        .withSelfRel());
        return Mono.just(resource);
    }

    public Flux<Author> toListModel(Flux<AuthorsEntity> entities, ServerWebExchange exchange) {
        if (Objects.isNull(entities)) {
            return Flux.empty();
        }
        return Flux.from(entities.map(e -> entityToModel(e, exchange)));
    }

    public Author entityToModel(AuthorsEntity entity, ServerWebExchange exchange) {
        Author resource = new Author();
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
