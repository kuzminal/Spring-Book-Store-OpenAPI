package com.kuzmin.bookstore.service;

import com.kuzmin.bookstore.api.model.Genre;
import com.kuzmin.bookstore.model.GenreEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GenreService {
    public Genre convertGenreToDTO(GenreEntity genreEntity) {
        Genre genre = new Genre();
        if (!Objects.isNull(genreEntity)) {
            genre.setId(genreEntity.getId());
            genre.setName(genreEntity.getName());
        }
        return genre;
    }
}
