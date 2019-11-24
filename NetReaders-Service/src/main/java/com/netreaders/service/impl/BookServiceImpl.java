package com.netreaders.service.impl;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Book;
import com.netreaders.models.ResponseMessage;
import com.netreaders.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    public ResponseMessage<Book> findBookById(Integer id) throws DataBaseSQLException {

        ResponseMessage<Book> message = new ResponseMessage<>();
        Book book = bookDao.getById(id);
        message.setObj(modelToDto(book));

        return message;
    }

    public ResponseMessage<Collection<Book>> findBooksByGenre(Integer genre_id, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<Book>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.findBooksByGenre(genre_id, amount, offset);
        message.setObj(createDtoCollection(books));

        return message;
    }

    public ResponseMessage<Collection<Book>> findBooksByAuthor(Integer author_id, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<Book>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.findBooksByAuthor(author_id, amount, offset);
        message.setObj(createDtoCollection(books));

        return message;
    }

    public ResponseMessage<Collection<Book>> findByName(String name, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<Book>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.getByName(name.toLowerCase(), amount, offset);
        message.setObj(createDtoCollection(books));

        return message;
    }

    public ResponseMessage<Collection<Book>> findAll(Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<Book>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.getById(amount, offset);
        message.setObj(createDtoCollection(books));

        return message;
    }

    public ResponseMessage<Integer> getCountByGenre(Integer id) {

        ResponseMessage<Integer> message = new ResponseMessage<>();
        Integer count = bookDao.getCountByGenre(id);
        message.setObj(count);

        return message;
    }

    public ResponseMessage<Integer> getCountByAuthor(Integer id) {

        ResponseMessage<Integer> message = new ResponseMessage<>();
        Integer count = bookDao.getCountByAuthor(id);
        message.setObj(count);

        return message;
    }

    public ResponseMessage<Integer> getCountByName(String name) {

        ResponseMessage<Integer> message = new ResponseMessage<>();
        Integer count = bookDao.getCountByName(name);
        message.setObj(count);

        return message;
    }

    public ResponseMessage<Integer> getCount() {

        ResponseMessage<Integer> message = new ResponseMessage<>();
        Integer count = bookDao.getCount();
        message.setObj(count);

        return message;
    }

    public Collection<Book> getByAnnouncementId(Integer announcement_id) {
        return createDtoCollection(bookDao.getByAnnouncementId(announcement_id));
    }

    public Collection<Book> createDtoCollection(Collection<Book> books) {

        return books.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    public Book modelToDto(Book book) throws DataBaseSQLException {

        book.setGenres(genreDao.getByBookId(book.getId()));
        book.setAuthors(authorDao.getByBookId(book.getId()));

        return book;

    }
}

