package com.netreaders.dao.author;

import com.netreaders.models.Author;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {

        Author author = new Author();

        author.setId(rs.getInt("author_id"));
        author.setName(rs.getString("author_name"));

        return author;
    }

    @Bean
    public AuthorMapper getAuthorMapper() {
        return new AuthorMapper();
    }
}
