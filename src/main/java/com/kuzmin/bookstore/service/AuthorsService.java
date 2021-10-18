package com.kuzmin.bookstore.service;

import com.kuzmin.bookstore.api.model.Author;
import com.kuzmin.bookstore.model.AuthorsEntity;
import com.kuzmin.bookstore.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuthorsService {
    private final AuthorRepository authorRepository;

    public AuthorsService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorsEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author convertAuthorToDTO(AuthorsEntity authorsEntity) {
        Author authors = new Author();
        if (!Objects.isNull(authorsEntity)) {
            authors.setId(authorsEntity.getId());
            authors.setName(authorsEntity.getName());
            authors.setFirstName(authorsEntity.getFirstName());
            authors.setLastName(authorsEntity.getLastName());
            authors.setEmail(authorsEntity.getEmail());
        }
        return authors;
    }
}
