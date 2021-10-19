package com.kuzmin.bookstore.service;

import com.kuzmin.bookstore.model.BookEntity;
import com.kuzmin.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Flux<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public Mono<BookEntity> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
