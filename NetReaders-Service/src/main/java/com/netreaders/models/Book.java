package com.netreaders.models;

import java.util.Date;

public class Book {
	protected int id;
	protected String title;
	protected int photo;
	protected String description;
	protected Date release_date;
	protected String book_language;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPhoto() {
		return photo;
	}
	public void setPhoto(int photo) {
		this.photo = photo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRelease_date() {
		return release_date;
	}
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}
	public String getBook_language() {
		return book_language;
	}
	public void setBook_language(String book_language) {
		this.book_language = book_language;
	}
	public Book() {};
	public Book(int id, String title, int photo, String description, Date release_date, String book_language) {
		this.id = id;
		this.title = title;
		this.photo = photo;
		this.description = description;
		this.release_date = release_date;
		this.book_language = book_language;
	}
	
	
	
}
