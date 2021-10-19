package com.kuzmin.bookstore.repository;

import com.kuzmin.bookstore.model.BookEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends R2dbcRepository<BookEntity, Long> {
}
