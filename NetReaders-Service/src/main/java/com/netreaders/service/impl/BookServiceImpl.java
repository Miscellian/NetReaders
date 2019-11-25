package com.netreaders.service.impl;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Book;
import com.netreaders.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    public Book findBookById(Integer id) throws DataBaseSQLException {

        Book book = bookDao.getById(id);
        return modelToDto(book);
    }

    public Collection<Book> findBooksByGenre(Integer genre_id, Integer amount, Integer offset) throws DataBaseSQLException {

        Collection<Book> books = bookDao.findBooksByGenre(genre_id, amount, offset);
        return createDtoCollection(books);
    }

    public Collection<Book> findBooksByAuthor(Integer author_id, Integer amount, Integer offset) throws DataBaseSQLException {

        Collection<Book> books = bookDao.findBooksByAuthor(author_id, amount, offset);
        return createDtoCollection(books);
    }

    public Collection<Book> findByName(String name, Integer amount, Integer offset) throws DataBaseSQLException {

        Collection<Book> books = bookDao.findBooksByName(name.toLowerCase(), amount, offset);
        return createDtoCollection(books);
    }

    public Collection<Book> findAll(Integer amount, Integer offset) throws DataBaseSQLException {

        Collection<Book> books = bookDao.findAllBooks(amount, offset);
        return createDtoCollection(books);
    }

    public Integer getCountByGenre(Integer id) {

        Integer count = bookDao.getCountByGenre(id);
        return count;
    }

    public Integer getCountByAuthor(Integer id) {

        Integer count = bookDao.getCountByAuthor(id);
        return count;
    }

    public Integer getCountByName(String name) {

        Integer count = bookDao.getCountByName(name);
        return count;
    }

    public Integer getCount() {

        Integer count = bookDao.getCount();
        return count;
    }

    public Collection<Book> getByAnnouncementId(Integer announcement_id) {

        return createDtoCollection(bookDao.findBooksByAnnouncement(announcement_id));
    }

    private Collection<Book> createDtoCollection(Collection<Book> books) {

        return books.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private Book modelToDto(Book book) throws DataBaseSQLException {

        book.setGenres(genreDao.getByBookId(book.getId()));
        book.setAuthors(authorDao.getByBookId(book.getId()));
        return book;

    }
}

