package com.netreaders.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private int id;
    private int rating;
    private String description;
    private boolean published;
    private Book book;
}
