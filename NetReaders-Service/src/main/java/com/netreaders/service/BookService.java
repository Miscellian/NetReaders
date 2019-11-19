package com.netreaders.service;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
import com.netreaders.dto.BookDto;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Book;
import com.netreaders.models.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private AuthorDao authorDao;

    public ResponseMessage<Collection<BookDto>> findBooksByGenre(Integer genre_id, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.findBooksByGenre(genre_id, amount, offset);
        Collection<BookDto> bookDtos = books.stream()
                .map(this::tryCreateBookDto)
                .collect(Collectors.toCollection(ArrayList::new));
        message.setObj(bookDtos);

        return message;
    }

    public ResponseMessage<Collection<BookDto>> findBooksByAuthor(Integer author_id, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.findBooksByAuthor(author_id, amount, offset);
        Collection<BookDto> bookDtos = books.stream()
                .map(this::tryCreateBookDto)
                .collect(Collectors.toCollection(ArrayList::new));
        message.setObj(bookDtos);

        return message;
    }

    public ResponseMessage<Collection<BookDto>> getById(Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.getById(amount, offset);
        Collection<BookDto> bookDtos = books.stream()
                .map(this::tryCreateBookDto)
                .collect(Collectors.toCollection(ArrayList::new));
        message.setObj(bookDtos);

        return message;
    }

    public ResponseMessage<BookDto> findBookById(Integer id) throws DataBaseSQLException {

        ResponseMessage<BookDto> message = new ResponseMessage<>();
        Book book = bookDao.getById(id);
        message.setObj(tryCreateBookDto(book));

        return message;
    }

    public ResponseMessage<Collection<BookDto>> getByName(String name) throws DataBaseSQLException {

        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.getByName(name.toLowerCase());
        Collection<BookDto> bookDtos = books.stream()
                .map(this::tryCreateBookDto)
                .collect(Collectors.toCollection(ArrayList::new));
        message.setObj(bookDtos);

        return message;
    }

    private BookDto tryCreateBookDto(Book book) throws DataBaseSQLException {

        return new BookDto(
                genreDao.getByBookId(book.getId()),
                authorDao.getByBookId(book.getId()),
                book);
    }
}
