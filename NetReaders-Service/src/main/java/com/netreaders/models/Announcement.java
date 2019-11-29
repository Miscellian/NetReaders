package com.netreaders.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    private Integer id;
    private Date announcement_date;
    private String description;
    private Boolean published;

    //foreign connections
    private Collection<Book> books;
}
