package com.netreaders.service.impl;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
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

    @Override
    public Book findBookById(Integer id) {

        Book book = bookDao.getById(id);
        return modelToDto(book);
    }

    @Override
    public Collection<Book> getBooksById(Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findAll(amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> getBooksByName(String name, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByName(name.toLowerCase(), amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> getByBooksUsername(String username, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByUsername(username.toLowerCase(), amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> findBooksByGenre(Integer genreId, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByGenre(genreId, amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Collection<Book> findBooksByAuthor(Integer authorId, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByAuthor(authorId, amount, offset);
        return createDtoCollection(books);
    }

    @Override
    public Integer getCount() {

        return bookDao.getCount();
    }

    @Override
    public Integer getCountByAuthor(Integer id) {

        return bookDao.getCountByAuthor(id);
    }

    @Override
    public Integer getCountByGenre(Integer id) {

        return bookDao.getCountByGenre(id);
    }

    @Override
    public Integer getCountByName(String name) {

        return bookDao.getCountByName(name);
    }

    @Override
    public Integer getCountByUsername(String username) {
        return bookDao.getCountByUsername(username);
    }

    @Override
    public Collection<Book> findByAnnouncementId(Integer announcementId) {

        return bookDao.findByAnnouncementId(announcementId);
    }

    @Override
    public Collection<Book> findByAnnouncementWithGenre(Integer announcementId, Integer genreId) {

        return bookDao.findByAnnouncementWithGenre(announcementId, genreId);
    }

    @Override
    public Collection<Book> findByAnnouncementWithAuthor(Integer announcementId, Integer authorId) {

        return bookDao.findByAnnouncementWithAuthor(announcementId, authorId);
    }

    private Collection<Book> createDtoCollection(Collection<Book> books) {

        return books.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private Book modelToDto(Book book) {

        book.setGenres(genreDao.getByBookId(book.getId()));
        book.setAuthors(authorDao.getByBookId(book.getId()));

        return book;
    }
}
