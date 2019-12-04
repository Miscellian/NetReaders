package com.netreaders.dao.book;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Book;

import java.util.Collection;

public interface BookDao extends GenericDao<Book, Integer> {

    Collection<Book> findByGenre(Integer genreId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Book> findByAuthor(Integer authorId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Book> findByUsername(String username, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Book> findByName(String name, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Book> findAll(Integer amount, Integer offset) throws DataBaseSQLException;

    Integer getCount() throws DataBaseSQLException;

    Integer getCountByAuthor(Integer authorId) throws DataBaseSQLException;

    Integer getCountByGenre(Integer genreId) throws DataBaseSQLException;

    Integer getCountByName(String name) throws DataBaseSQLException;

    Integer getCountByUsername(String username) throws DataBaseSQLException;

    Integer getFavouritesCountByUsername(String username);

    Collection<Book> findByAnnouncementId(Integer announcementId) throws DataBaseSQLException;

    Collection<Book> findByAnnouncementWithGenre(Integer announcementId, Integer genreId) throws DataBaseSQLException;

    Collection<Book> findByAnnouncementWithAuthor(Integer announcementId, Integer authorId) throws DataBaseSQLException;

    Book getByReviewId(int id);

    void addBookToUserLibrary(String username, Integer bookId);

    boolean checkIfBookInUserLibrary(String username, Integer bookId);

    void removeBookFromUserLibrary(String username, Integer bookId);

    boolean checkIfBookInFavourites(String username, Integer bookId);

    void addBookToUserFavourites(String username, Integer bookId);

    void removeBookToUserFavourites(String username, Integer bookId);

    Collection<Book> findByFavouritesUsername(String username, Integer amount, Integer offset);

}