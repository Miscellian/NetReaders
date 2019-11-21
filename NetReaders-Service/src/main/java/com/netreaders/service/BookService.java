package com.netreaders.service;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
import com.netreaders.dto.BookDto;
import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Book;
import com.netreaders.models.ResponseMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookDao bookDao;

    private final GenreDao genreDao;

    private final AuthorDao authorDao;

    public BookService(BookDao bookDao, GenreDao genreDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.authorDao = authorDao;
    }

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

    public ResponseMessage<Collection<BookDto>> getByName(String name, Integer amount, Integer offset) throws DataBaseSQLException {

        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        Collection<Book> books = bookDao.getByName(name.toLowerCase(), amount, offset);
        Collection<BookDto> bookDtos = books.stream()
                .map(this::tryCreateBookDto)
                .collect(Collectors.toCollection(ArrayList::new));
        message.setObj(bookDtos);

        return message;
    }

    public ResponseMessage<Integer> getCount() {

        ResponseMessage<Integer> message = new ResponseMessage<>();
        Integer count = bookDao.getCount();
        message.setObj(count);

        return message;
    }

    public ResponseMessage<Integer> getCountByAuthor(Integer id) {

        ResponseMessage<Integer> message = new ResponseMessage<>();
        Integer count = bookDao.getCountByAuthor(id);
        message.setObj(count);

        return message;
    }

    public ResponseMessage<Integer> getCountByGenre(Integer id) {

        ResponseMessage<Integer> message = new ResponseMessage<>();
        Integer count = bookDao.getCountByGenre(id);
        message.setObj(count);

        return message;
    }

    public ResponseMessage<Integer> getCountByName(String name) {

        ResponseMessage<Integer> message = new ResponseMessage<>();
        Integer count = bookDao.getCountByName(name);
        message.setObj(count);

        return message;
    }

    private BookDto tryCreateBookDto(Book book) throws DataBaseSQLException {

        return new BookDto(
                genreDao.getByBookId(book.getId()),
                authorDao.getByBookId(book.getId()),
                book);
    }
}
