package com.kuzmin.bookstore.service;

import com.kuzmin.bookstore.model.AuthorsEntity;
import com.kuzmin.bookstore.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorsService {
    private final AuthorRepository authorRepository;

    public AuthorsService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Flux<AuthorsEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Mono<AuthorsEntity> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
