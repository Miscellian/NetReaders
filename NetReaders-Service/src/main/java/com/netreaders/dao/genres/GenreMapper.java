package com.netreaders.dao.genres;

import com.netreaders.models.Genre;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {

        Genre genre = new Genre();

        genre.setId(rs.getInt("genre_id"));
        genre.setName(rs.getString("genre_name"));

        return genre;
    }

    @Bean
    public GenreMapper getGenreMapper() {
        return new GenreMapper();
    }
}
