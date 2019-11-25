package com.netreaders.dao.book;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Book;

import java.util.Collection;

public interface BookDao extends GenericDao<Book, Integer> {

    Collection<Book> findBooksByGenre(int genre_id, int amount, int offset) throws DataBaseSQLException;

    Collection<Book> findBooksByAuthor(int author_id, int amount, int offset) throws DataBaseSQLException;

    Collection<Book> findAllBooks(int amount, int offset) throws DataBaseSQLException;

    Collection<Book> findBooksByName(String name, int amount, int offset) throws DataBaseSQLException;

    Collection<Book> findBooksByAnnouncement(int announcement_id) throws DataBaseSQLException;

    Integer getCount() throws DataBaseSQLException;

    Integer getCountByAuthor(int author_id) throws DataBaseSQLException;

    Integer getCountByGenre(int genre_id) throws DataBaseSQLException;

    Integer getCountByName(String name) throws DataBaseSQLException;
}