package com.kuzmin.bookstore.repository;

import com.kuzmin.bookstore.model.AuthorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorsEntity, Long> {
}
