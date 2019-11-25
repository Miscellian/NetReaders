package com.netreaders.service;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Book;

import java.util.Collection;

public interface BookService {

    Book findBookById(Integer bookId) throws DataBaseSQLException;

    Collection<Book> findBooksByGenre(Integer genreId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Book> findBooksByAuthor(Integer authorId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Book> findByName(String bookName, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Book> findAll(Integer amount, Integer offset) throws DataBaseSQLException;

    Integer getCountByGenre(Integer genreId) throws DataBaseSQLException;

    Integer getCountByAuthor(Integer authorId) throws DataBaseSQLException;

    Integer getCountByName(String bookName) throws DataBaseSQLException;

    Integer getCount() throws DataBaseSQLException;
}
