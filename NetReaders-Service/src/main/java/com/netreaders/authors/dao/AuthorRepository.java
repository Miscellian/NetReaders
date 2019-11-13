package com.netreaders.authors.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.netreaders.authors.dao.interfaces.AuthorDataStore;
import com.netreaders.models.Author;

@Repository
public class AuthorRepository implements AuthorDataStore {
	
	@Autowired
	private JdbcTemplate template;

	public Collection<Author> getAll() throws SQLException{
		try {
		String query = "select * from authors";
		List<Author> authors = template.query(query,
				(rs, rowNum) -> new Author(rs.getInt("author_id"),rs.getString("author_name"))
		);
		return authors;
		}catch(Exception e) {
			throw new SQLException("Internal sql exception");
		}
	}

	public Author getById(int id) throws SQLException{
		try {
		String query = "select * from authors where author_id=?";
		Author author = template.queryForObject(query, new Object[]{id},
				(rs, rowNum) -> new Author(rs.getInt("author_id"),rs.getString("author_name")));
		return author;
		}catch(Exception e) {
			throw new SQLException("Internal sql exception");
		}
	}
	
	public Collection<Author> getByBookId(int id) throws SQLException{
		try {
		String query = "select a.author_id, a.author_name from authors a " + 
				"inner join book_author ba on ba.author_id = a.author_id " + 
				"where ba.book_id = ?";
		List<Author> authors = template.query(query, new Object[]{id},
				(rs, rowNum) -> new Author(rs.getInt("author_id"),rs.getString("author_name")));
		return authors;
		}catch(Exception e) {
			throw new SQLException("Internal sql exception");
		}
	}

}
