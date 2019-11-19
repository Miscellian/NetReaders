package com.netreaders.dao.book;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Book;

import java.util.Collection;

public interface BookDao extends GenericDao<Book, Integer> {

    Collection<Book> findBooksByGenre(int genre_id, int amount, int offset) throws DataBaseSQLException;

    Collection<Book> findBooksByAuthor(int author_id, int amount, int offset) throws DataBaseSQLException;

    Collection<Book> getById(int amount, int offset) throws DataBaseSQLException;

    Collection<Book> getByName(String name) throws DataBaseSQLException;

    Collection<Book> getByAnnouncementId(int announcement_id) throws DataBaseSQLException;
}