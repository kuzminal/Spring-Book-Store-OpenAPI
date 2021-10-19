package com.kuzmin.bookstore.service;

import com.kuzmin.bookstore.model.GenreEntity;
import com.kuzmin.bookstore.repository.GenreRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Flux<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }

    public Mono<GenreEntity> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public Mono<Void> deleteById(Long id) {
        return genreRepository.deleteById(id);
    }
}
