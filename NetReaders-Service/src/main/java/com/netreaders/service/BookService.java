package com.netreaders.service;

import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Book;
import com.netreaders.models.ResponseMessage;

import java.util.Collection;

public interface BookService {

    ResponseMessage<Book> findBookById(Integer bookId) throws DataBaseSQLException;

    ResponseMessage<Collection<Book>> findBooksByGenre(Integer genreId, Integer amount, Integer offset) throws DataBaseSQLException;

    ResponseMessage<Collection<Book>> findBooksByAuthor(Integer authorId, Integer amount, Integer offset) throws DataBaseSQLException;

    ResponseMessage<Collection<Book>> findByName(String bookName, Integer amount, Integer offset) throws DataBaseSQLException;

    ResponseMessage<Collection<Book>> findAll(Integer amount, Integer offset) throws DataBaseSQLException;

    ResponseMessage<Integer> getCountByGenre(Integer genreId) throws DataBaseSQLException;

    ResponseMessage<Integer> getCountByAuthor(Integer authorId) throws DataBaseSQLException;

    ResponseMessage<Integer> getCountByName(String bookName) throws DataBaseSQLException;

    ResponseMessage<Integer> getCount() throws DataBaseSQLException;
}
