package com.netreaders.dao.book;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Book;

import java.util.Collection;

public interface BookDao extends GenericDao<Book, Integer> {

    Collection<Book> findByGenre(int genre_id, int amount, int offset) throws DataBaseSQLException;

    Collection<Book> findByAuthor(int author_id, int amount, int offset) throws DataBaseSQLException;

    Collection<Book> findAll(int amount, int offset) throws DataBaseSQLException;

    Collection<Book> findByName(String name, int amount, int offset) throws DataBaseSQLException;

    Integer getCount() throws DataBaseSQLException;

    Integer getCountByAuthor(int author_id) throws DataBaseSQLException;

    Integer getCountByGenre(int genre_id) throws DataBaseSQLException;

    Integer getCountByName(String name) throws DataBaseSQLException;

    Collection<Book> findByAnnouncementId(Integer announcementId) throws DataBaseSQLException;

    Collection<Book> findByAnnouncementWithGenre(Integer announcementId, Integer genreId) throws DataBaseSQLException;

    Collection<Book> findByAnnouncementWithAuthor(Integer announcementId, Integer authorId) throws DataBaseSQLException;

}