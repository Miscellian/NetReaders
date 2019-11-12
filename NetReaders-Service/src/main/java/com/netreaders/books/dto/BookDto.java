package com.netreaders.books.dto;

import java.util.Collection;
import java.util.Date;

import com.netreaders.models.Author;
import com.netreaders.models.Book;
import com.netreaders.models.Genre;

public class BookDto{
	private Collection<Genre> genres;
	private Collection<Author> authors;
	private Book book;
	public Collection<Genre> getGenres() {
		return genres;
	}
	public void setGenres(Collection<Genre> genres) {
		this.genres = genres;
	}
	public Collection<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(Collection<Author> authors) {
		this.authors = authors;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public BookDto(Collection<Genre> genres, Collection<Author> authors, Book book) {
		this.genres = genres;
		this.authors = authors;
		this.book = book;
	}
	
	
	
}
