package com.kuzmin.bookstore.repository;

import com.kuzmin.bookstore.model.AuthorsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends R2dbcRepository<AuthorsEntity, Long> {
}
