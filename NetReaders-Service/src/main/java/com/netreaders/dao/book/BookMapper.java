package com.netreaders.dao.book;

import com.netreaders.models.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

        Book book = new Book();

        book.setId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setPhoto(rs.getInt("photo"));
        book.setDescription(rs.getString("description"));
        book.setRelease_date(rs.getDate("release_date"));
        book.setBook_language(rs.getString("book_language"));
        book.setPublished(rs.getBoolean("published"));

        return book;
    }

    @Bean
    public BookMapper getBookMapper() {
        return new BookMapper();
    }
}
