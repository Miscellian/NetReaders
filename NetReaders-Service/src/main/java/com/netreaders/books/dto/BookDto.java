package com.netreaders.books.dto;

import java.util.Date;

import com.netreaders.models.Author;
import com.netreaders.models.Book;
import com.netreaders.models.Genre;

public class BookDto{
	private Genre genre;
	private Author author;
	private Book book;
	
	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookDto(Genre genre, Author author, Book book) {
		this.genre = genre;
		this.author = author;
		this.book = book;
	}
	
	
}
