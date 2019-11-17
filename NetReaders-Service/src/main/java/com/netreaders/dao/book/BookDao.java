package com.netreaders.dao.book;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.Book;

import java.sql.SQLException;
import java.util.Collection;

public interface BookDao extends GenericDao<Book, Integer> {

    Collection<Book> findBooksByGenre(int genre_id, int amount, int offset) throws SQLException;

    Collection<Book> findBooksByAuthor(int author_id, int amount, int offset) throws SQLException;

    Collection<Book> getById(int amount, int offset) throws SQLException;
    
    Collection<Book> getByName(String name) throws SQLException;
}