package com.netreaders.models;

import com.netreaders.dto.BookDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	private int reviewId;
	private int rating;
	private String description;
	private boolean published;
	private BookDto book;
}
