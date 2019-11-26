package com.netreaders.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Collection;

@Data
@NoArgsConstructor
public class Announcement {

    private Integer id;
    private Date announcement_date;
    private String description;
    private Boolean published;

    //foreign connections
    private Collection<Book> books;
}
