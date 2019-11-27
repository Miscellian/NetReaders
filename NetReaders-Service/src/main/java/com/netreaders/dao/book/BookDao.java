package com.netreaders.dao.book;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.Book;

import java.sql.SQLException;
import java.util.Collection;

public interface BookDao extends GenericDao<Book, Integer> {

    Collection<Book> findBooksByGenre(int genre_id, int amount, int offset) throws SQLException;

    Collection<Book> findBooksByAuthor(int author_id, int amount, int offset) throws SQLException;

    Collection<Book> getById(int amount, int offset) throws SQLException;
    
    Collection<Book> getByName(String name, int amount, int offset) throws SQLException;

    Collection<Book> getByUsername(String username, int amount, int offset) throws SQLException;

    Integer getCount() throws SQLException;
    
    Integer getCountByAuthor(int author_id) throws SQLException;

    Integer getCountByGenre(int genre_id) throws SQLException;
    
    Integer getCountByName(String name) throws SQLException;
}