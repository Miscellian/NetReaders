package com.netreaders.authors.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.netreaders.authors.dao.interfaces.AuthorDataStore;
import com.netreaders.models.Author;
import com.netreaders.models.Genre;

@Repository
public class AuthorRepository implements AuthorDataStore {
	
	@Autowired
	private JdbcTemplate template;

	public Collection<Author> getAll() {
		String query = "select * from authors";
		List<Author> authors = template.query(query,
				(rs, rowNum) -> new Author(rs.getInt("author_id"),rs.getString("author_name"))
		);
		return authors;
	}

	public Author getById(int id) {
		String query = "select * from authors where author_id=?";
		Author author = template.queryForObject(query, new Object[]{id},
				(rs, rowNum) -> new Author(rs.getInt("author_id"),rs.getString("author_name")));
		return author;
	}
	
	public Author getByBookId(int id) {
		String query = "select a.author_id, a.author_name from authors a " + 
				"inner join book_author ba on ba.author_id = a.author_id " + 
				"where ba.book_id = ?";
		Author author = template.queryForObject(query, new Object[]{id},
				(rs, rowNum) -> new Author(rs.getInt("author_id"),rs.getString("author_name")));
		return author;
	}

}
