package com.kuzmin.bookstore.controller;

import com.kuzmin.bookstore.api.BooksApi;
import com.kuzmin.bookstore.api.model.Author;
import com.kuzmin.bookstore.api.model.Book;
import com.kuzmin.bookstore.api.model.Genre;
import com.kuzmin.bookstore.hateoas.BookRepresentationModelAssembler;
import com.kuzmin.bookstore.model.AuthorsEntity;
import com.kuzmin.bookstore.model.BookEntity;
import com.kuzmin.bookstore.model.GenreEntity;
import com.kuzmin.bookstore.service.AuthorsService;
import com.kuzmin.bookstore.service.BookService;
import com.kuzmin.bookstore.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class BooksController implements BooksApi {
    private final BookService bookService;
    private final BookRepresentationModelAssembler assembler;
    private final AuthorsService authorsService;
    private final GenreService genreService;

    public BooksController(BookService bookService, BookRepresentationModelAssembler assembler, AuthorsService authorsService, GenreService genreService) {
        this.bookService = bookService;
        this.assembler = assembler;
        this.authorsService = authorsService;
        this.genreService = genreService;
    }

    @Override
    public ResponseEntity<List<Book>> queryBooks(
            @Valid @RequestParam(value = "id", required = false) String id,
            @Valid @RequestParam(value = "title", required = false) String title,
            @Valid @RequestParam(value = "ISBN", required = false) String ISBN,
            @Valid @RequestParam(value = "page", required = false, defaultValue="1") Integer page,
            @Valid @RequestParam(value = "size", required = false, defaultValue="10") Integer size) {
        return ok(assembler.toListModel(bookService.getAllBooks()));
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
            book.setGenre(genreService.convertGenreToDTO(bookEntity.getGenre()));
            book.setAuthors(bookEntity.getAuthors()
                    .stream()
                    .map(authorsService::convertAuthorToDTO)
                    .collect(Collectors.toList()));
        }
        return book;
    }
}
