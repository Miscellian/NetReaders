package com.netreaders.genres.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.netreaders.genres.dao.interfaces.GenreDataStore;
import com.netreaders.models.Genre;

@Repository
public class GenreRepository implements GenreDataStore{
	
	@Autowired
	private JdbcTemplate template;

	//TODO implement logger
	
	public Collection<Genre> getAll() throws SQLException {
		try {
			String query = "select * from genres";
			List<Genre> genres = template.query(query,
					(rs, rowNum) ->
						new Genre(rs.getInt("genre_id"),rs.getString("genre_name"))
			);
			return genres;
		}catch(Exception e) {
			throw new SQLException("Internal sql exception");
		}
	}

	public Genre getById(int id) throws SQLException {
		try {
			String query = "select * from genres where genre_id=?";
			Genre genre = template.queryForObject(query, new Object[]{id},
					(rs, rowNum) -> new Genre(rs.getInt("genre_id"),rs.getString("genre_name")));
			return genre;
		}catch(Exception e) {
			throw new SQLException("Internal sql exception");
		}
	}

	public Collection<Genre> getByBookId(int id) throws SQLException {
		try {
		String query = "select distinct g.genre_id, g.genre_name from genres g " + 
				"inner join book_genre bg on bg.genre_id = g.genre_id " + 
				"where bg.book_id = ?";
		List<Genre> genres = template.query(query, new Object[]{id},
				(rs, rowNum) -> new Genre(rs.getInt("genre_id"),rs.getString("genre_name")));
		return genres;
		} catch(Exception e) {
			throw new SQLException("Internal sql exception");
		}
	}
		
}
