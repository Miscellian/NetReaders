package com.netreaders.service;

import com.netreaders.dao.author.AuthorDao;
import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.genres.GenreDao;
import com.netreaders.dao.user.UserDao;
import com.netreaders.dto.BookDto;
import com.netreaders.models.Book;
import com.netreaders.models.ResponseMessage;
import com.netreaders.models.User;
import com.netreaders.utils.ResponseMessagePrepearer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    @Autowired
    public UserService(UserDao userDao, BookDao bookDao,
                       GenreDao genreDao, AuthorDao authorDao) {
        this.userDao = userDao;
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

    public ResponseMessage<User> findUserByUsername(String username) {
        ResponseMessage<User> message = new ResponseMessage<>();
        try {
            User user = userDao.findByUsername(username);
            message.setObj(user);
        } catch (SQLException | RuntimeException e) {
            ResponseMessagePrepearer.prepareMessage(message, e.getMessage());
        }
        return message;
    }

    public ResponseMessage<Collection<BookDto>> getBookListByUsername(String username, String amount, String offset) {
        ResponseMessage<Collection<BookDto>> message = new ResponseMessage<>();
        try {
            int numericAmount = Integer.parseInt(amount);
            int numericOffset = Integer.parseInt(offset);
            Collection<Book> books = bookDao.getByUsername(username, numericAmount,numericOffset);
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
}
