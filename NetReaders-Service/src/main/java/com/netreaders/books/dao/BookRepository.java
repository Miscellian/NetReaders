package com.netreaders.books.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.netreaders.books.dao.interfaces.*;
import com.netreaders.models.Book;

@Repository
public class BookRepository implements BookDataStore {
	
	@Autowired
	private JdbcTemplate template;

	public Book findBookById(int id) {
		String query = "select * from books where book_id=?";
		Book book = template.queryForObject(query, new Object[]{id},
				(rs, rowNum) -> new Book(rs.getInt("book_id"),
										 rs.getString("title"),
										 rs.getInt("photo"),
										 rs.getString("description"),
										 rs.getDate("release_date"),
										 rs.getString("book_language")));
		return book;
	}
	public Collection<Book> findBooksByGenre(int genre_id, int amount, int offset) {
		String query = "select b.book_id, b.title, b.photo, b.description, b.release_date, b.book_language from books b " + 
				"inner join book_genre bg ON bg.book_id = b.book_id " + 
				"where bg.genre_id = ? limit ? offset ?";
		List<Book> books = executeQuery(query, new Object[] {genre_id, amount, offset});
		return books;
	}
	public Collection<Book> findBooksByAuthor(int author_id, int amount, int offset) {
		String query = "select b.book_id, b.title, b.photo, b.description, b.release_date, b.book_language from books b " + 
				"inner join book_author ba ON ba.book_id = b.book_id " + 
				"where ba.author_id = ? limit ? offset ?";
		List<Book> books = executeQuery(query, new Object[] {author_id, amount, offset});
		return books;
	}
	public Collection<Book> getById(int amount, int offset) {
		String query = "select * from books order by book_id limit ? offset ?";
		List<Book> books = executeQuery(query, new Object[] {amount, offset});
		return books;
	}
	
	private List<Book> executeQuery(String query, Object[] args){
		return template.query(query,args,
				(rs, rowNum) ->
				new Book(rs.getInt("book_id"),
						rs.getString("title"),
						rs.getInt("photo"),
						rs.getString("description"),
						rs.getDate("release_date"),
						rs.getString("book_language")));
	}

}
