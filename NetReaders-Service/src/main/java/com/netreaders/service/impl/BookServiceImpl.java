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

    public Book findById(Integer id) {

        Book book = bookDao.getById(id);
        return modelToDto(book);
    }

    public Collection<Book> findByGenre(Integer genre_id, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByGenre(genre_id, amount, offset);
        return createDtoCollection(books);
    }

    public Collection<Book> findByAuthor(Integer author_id, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByAuthor(author_id, amount, offset);
        return createDtoCollection(books);
    }

    public Collection<Book> findByName(String name, Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findByName(name.toLowerCase(), amount, offset);
        return createDtoCollection(books);
    }

    public Collection<Book> findAll(Integer amount, Integer offset) {

        Collection<Book> books = bookDao.findAll(amount, offset);
        return createDtoCollection(books);
    }

    public Integer getCountByGenre(Integer id) {

        return bookDao.getCountByGenre(id);
    }

    public Integer getCountByAuthor(Integer id) {

        return bookDao.getCountByAuthor(id);
    }

    public Integer getCountByName(String name) {

        return bookDao.getCountByName(name);
    }

    public Integer getCount() {

        return bookDao.getCount();
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

