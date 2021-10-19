package com.kuzmin.bookstore.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table("authors")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AuthorsEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String email;

    @ToString.Exclude
    private Set<BookEntity> books = new HashSet<>();

    public AuthorsEntity id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorsEntity that = (AuthorsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName, lastName, email, books);
    }
}
