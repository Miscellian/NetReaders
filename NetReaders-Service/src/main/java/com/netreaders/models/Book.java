package com.netreaders.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Integer id;
    private String title;
    private Integer photo;
    private String description;
    private Date release_date;
    private String book_language;

    // foreign connections
    private Collection<Genre> genres;
    private Collection<Author> authors;
}
