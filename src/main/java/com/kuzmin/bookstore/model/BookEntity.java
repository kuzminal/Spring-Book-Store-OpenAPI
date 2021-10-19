package com.kuzmin.bookstore.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table("books")
public class BookEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    @NotBlank
    private String annotation;

    private byte[] imageCover;

    @ToString.Exclude
    private GenreEntity genre;

    @ToString.Exclude
    private Set<AuthorsEntity> authors = new HashSet<>();

    private Double price;

    private Integer count;

    public BookEntity id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(isbn, that.isbn) && Objects.equals(annotation, that.annotation) && Arrays.equals(imageCover, that.imageCover) && Objects.equals(genre, that.genre) && Objects.equals(authors, that.authors) && Objects.equals(price, that.price) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, isbn, annotation, genre, authors, price, count);
        result = 31 * result + Arrays.hashCode(imageCover);
        return result;
    }
}
