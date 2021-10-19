package com.kuzmin.bookstore.repository;

import com.kuzmin.bookstore.model.GenreEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends R2dbcRepository<GenreEntity, Long> {
}
