package com.kuzmin.bookstore.controller;

import com.kuzmin.bookstore.api.ApiUtil;
import com.kuzmin.bookstore.api.BooksApi;
import com.kuzmin.bookstore.api.model.Authors;
import com.kuzmin.bookstore.api.model.Book;
import com.kuzmin.bookstore.api.model.Genre;
import com.kuzmin.bookstore.model.AuthorsEntity;
import com.kuzmin.bookstore.model.BookEntity;
import com.kuzmin.bookstore.model.GenreEntity;
import com.kuzmin.bookstore.service.BookService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class BooksController implements BooksApi {
    private final BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<List<Book>> queryBooks(
            @Valid @RequestParam(value = "id", required = false) String id,
            @Valid @RequestParam(value = "title", required = false) String title,
            @Valid @RequestParam(value = "ISBN", required = false) String ISBN,
            @Valid @RequestParam(value = "page", required = false, defaultValue="1") Integer page,
            @Valid @RequestParam(value = "size", required = false, defaultValue="10") Integer size) {
        List<Book> books = bookService.getAllBooks()
                .stream()
                .map(this::convertBookToDTO)
                .collect(Collectors.toList());
            return new ResponseEntity<>(books, HttpStatus.OK);
    }

    private Book convertBookToDTO(BookEntity bookEntity) {
        Book book = new Book();
        if (!Objects.isNull(bookEntity)) {
            book.setId(bookEntity.getId());
            book.setAnnotation(bookEntity.getAnnotation());
            book.setCount(bookEntity.getCount());
            book.setImageCover(bookEntity.getImageCover());
            book.setIsbn(bookEntity.getIsbn());
            book.setPrice(bookEntity.getPrice());
            book.setTitle(bookEntity.getTitle());
            book.setGenre(convertGenreToDTO(bookEntity.getGenre()));
            book.setAuthors(bookEntity.getAuthors()
                    .stream()
                    .map(this::convertAuthorToDTO)
                    .collect(Collectors.toList()));
        }
        return book;
    }

    private Genre convertGenreToDTO(GenreEntity genreEntity) {
        Genre genre = new Genre();
        if (!Objects.isNull(genreEntity)) {
            genre.setId(genreEntity.getId());
            genre.setName(genreEntity.getName());
        }
        return genre;
    }

    private Authors convertAuthorToDTO(AuthorsEntity authorsEntity) {
        Authors authors = new Authors();
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
