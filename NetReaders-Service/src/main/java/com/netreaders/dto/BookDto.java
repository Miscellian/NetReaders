package com.netreaders.dto;

import com.netreaders.models.Author;
import com.netreaders.models.Book;
import com.netreaders.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Collection<Genre> genres;
    private Collection<Author> authors;
    private Book book;
}
