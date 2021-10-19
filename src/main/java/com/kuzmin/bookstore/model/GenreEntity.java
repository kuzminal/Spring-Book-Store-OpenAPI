package com.kuzmin.bookstore.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Table("genres")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GenreEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    @NotBlank
    private String name;

    public GenreEntity id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
