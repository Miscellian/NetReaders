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


}
