package com.netreaders.service;

import com.netreaders.dto.UserBookLibrary;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Book;

import java.util.Collection;

public interface BookService {

    Book findBookById(Integer id);
    
    Book findBookByReviewId(Integer id);

    Collection<Book> getBooksById(Integer amount, Integer offset);

    Collection<Book> getBooksByName(String name, Integer amount, Integer offset);

    Collection<Book> getBooksUsername(String username, Integer amount, Integer offset);
    
    Collection<Book> getByUserPreferences(String username);

    Collection<Book> findBooksByGenre(Integer genreId, Integer amount, Integer offset);

    Collection<Book> findBooksByAuthor(Integer authorId, Integer amount, Integer offset);

    Collection<Book> findByAnnouncementId(Integer announcementId) throws DataBaseSQLException;

    Collection<Book> findByAnnouncementWithGenre(Integer announcementId, Integer genreId) throws DataBaseSQLException;

    Collection<Book> findByAnnouncementWithAuthor(Integer announcementId, Integer authorId) throws DataBaseSQLException;

    Integer getCount();

    Integer getCountByAuthor(Integer id);

    Integer getCountByGenre(Integer id);

    Integer getCountByName(String name);

    Integer getCountByUsername(String username);

    Integer getFavouritesCountByUsername(String username);

    Integer getToReadListCountByUsername(String username);

    void addBookToUserLibrary(UserBookLibrary userBookLibrary);

    boolean checkIfBookInLibrary(UserBookLibrary userBookLibrary);

    void removeBookFromUserLibrary(UserBookLibrary userBookLibrary);

    void addBookToUserFavourites(UserBookLibrary userBookLibrary);

    void removeBookFromUserFavourites(UserBookLibrary userBookLibrary);

    boolean checkIfBookInFavourites(UserBookLibrary userBookLibrary);

    void addBookToUserToReadList(UserBookLibrary userBookLibrary);

    void removeBookFromUserToReadList(UserBookLibrary userBookLibrary);

    boolean checkIfBookInUserToReadList(UserBookLibrary userBookLibrary);

    Collection<Book> getFavouritesByUsername(String username, Integer amount, Integer offset);

    Collection<Book> getToReadListByUsername(String username, Integer amount, Integer offset);
    
    Collection<Book> getUnpublished(Integer amount, Integer offset);
    
    void publishBook(Integer bookId);
    
    void addBook(Book book);
    
    Integer getUnpublishedCount();
    
    boolean checkBookExists(String title);
}
