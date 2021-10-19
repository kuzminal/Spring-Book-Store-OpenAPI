package com.kuzmin.bookstore.service;

import com.kuzmin.bookstore.model.GenreEntity;
import com.kuzmin.bookstore.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<GenreEntity> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
