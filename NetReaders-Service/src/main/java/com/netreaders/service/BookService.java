package com.netreaders.service;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
import com.netreaders.dto.BookDto;
import com.netreaders.models.Book;
import com.netreaders.models.ResponseMessage;
import com.netreaders.utils.ResponseMessagePrepearer;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    public BookService(BookDao bookDao,
                       GenreDao genreDao,
                       AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.authorDao = authorDao;
    }

    private BookDto tryCreateBookDto(Book book) throws RuntimeException {
        try {
            return new BookDto(
                    genreDao.getByBookId(book.getId()),
                    authorDao.getByBookId(book.getId()),
                    book);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseMessage<Collection<BookDto>> findBooksByGenre(String genre_id, String amount, String offset) {
        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        try {
            int numericGenreId = Integer.parseInt(genre_id);
            int numericAmount = Integer.parseInt(amount);
            int numericOffset = Integer.parseInt(offset);
            Collection<Book> books = bookDao.findBooksByGenre(numericGenreId, numericAmount, numericOffset);
            Collection<BookDto> bookDtos = books.stream()
                    .map(this::tryCreateBookDto)
                    .collect(Collectors.toCollection(ArrayList::new));
            message.setObj(bookDtos);
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Collection<BookDto>> findBooksByAuthor(String author_id, String amount, String offset) {
        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        try {
            int numericAuthorId = Integer.parseInt(author_id);
            int numericAmount = Integer.parseInt(amount);
            int numericOffset = Integer.parseInt(offset);
            Collection<Book> books = bookDao.findBooksByAuthor(numericAuthorId, numericAmount, numericOffset);
            Collection<BookDto> bookDtos = books.stream()
                    .map(this::tryCreateBookDto)
                    .collect(Collectors.toCollection(ArrayList::new));
            message.setObj(bookDtos);
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
        } catch (RuntimeException | SQLException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Collection<BookDto>> getById(String amount, String offset) {
        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        try {
            int numericAmount = Integer.parseInt(amount);
            int numericOffset = Integer.parseInt(offset);
            Collection<Book> books = bookDao.getById(numericAmount, numericOffset);
            Collection<BookDto> bookDtos = books.stream()
                    .map(this::tryCreateBookDto)
                    .collect(Collectors.toCollection(ArrayList::new));
            message.setObj(bookDtos);
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<BookDto> findBookById(String id) {
        ResponseMessage<BookDto> message = new ResponseMessage<>();
        try {
            int numericId = Integer.parseInt(id);
            Book book = bookDao.getById(numericId);
            message.setObj(tryCreateBookDto(book));
        } catch (NumberFormatException e) {
            ResponseMessagePrepearer.prepareMessage(message, "Invalid numeric parameters");
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Collection<BookDto>> getByName(String name, String amount, String offset) {
        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        try {
            int numericAmount = Integer.parseInt(amount);
            int numericOffset = Integer.parseInt(offset);
            Collection<Book> books = bookDao.getByName(name.toLowerCase(), numericAmount, numericOffset);
            Collection<BookDto> bookDtos = books.stream()
                    .map(this::tryCreateBookDto)
                    .collect(Collectors.toCollection(ArrayList::new));
            message.setObj(bookDtos);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Collection<BookDto>> getByUsername(String username, String amount, String offset) {
        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        try {
            int numericAmount = Integer.parseInt(amount);
            int numericOffset = Integer.parseInt(offset);
            Collection<Book> books = bookDao.getByUsername(username.toLowerCase(), numericAmount, numericOffset);
            Collection<BookDto> bookDtos = books.stream()
                    .map(this::tryCreateBookDto)
                    .collect(Collectors.toCollection(ArrayList::new));
            message.setObj(bookDtos);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Integer> getCount() {
        ResponseMessage<Integer> message = new ResponseMessage<>();
        try {
            Integer count = bookDao.getCount();
            message.setObj(count);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Integer> getCountByAuthor(String id) {
        ResponseMessage<Integer> message = new ResponseMessage<>();
        try {
            int numericId = Integer.parseInt(id);
            Integer count = bookDao.getCountByAuthor(numericId);
            message.setObj(count);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Integer> getCountByGenre(String id) {
        ResponseMessage<Integer> message = new ResponseMessage<>();
        try {
            int numericId = Integer.parseInt(id);
            Integer count = bookDao.getCountByGenre(numericId);
            message.setObj(count);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Integer> getCountByName(String name) {
        ResponseMessage<Integer> message = new ResponseMessage<>();
        try {
            Integer count = bookDao.getCountByName(name);
            message.setObj(count);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Integer> getCountByUsername(String username) {
        ResponseMessage<Integer> message = new ResponseMessage<>();
        try {
            Integer count = bookDao.getCountByUsername(username);
            message.setObj(count);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }
}
