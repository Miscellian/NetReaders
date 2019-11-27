package com.netreaders.dao.book;

import com.netreaders.models.Book;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Log4j
@PropertySource("classpath:query.properties")
@Repository
public class BookDaoImpl implements BookDao {

    private JdbcTemplate template;
    private Environment env;
    private BookMapper bookMapper;

    @Autowired
    public BookDaoImpl(JdbcTemplate template, Environment env, BookMapper bookMapper) {
        this.template = template;
        this.env = env;
        this.bookMapper = bookMapper;
    }

    @Override
    public Book create(Book book) throws SQLException {

        final String sql_query = env.getProperty("book.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateKeyException if record exist in table
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, book.getTitle());
                ps.setInt(2, book.getPhoto());
                ps.setString(3, book.getDescription());
                ps.setDate(4, book.getRelease_date());
                ps.setString(5, book.getBook_language());
                return ps;
            }, holder);

            Integer newId;
            if (holder.getKeys().size() > 1) {
                newId = (Integer) holder.getKeys().get("book_id");
            } else {
                newId = holder.getKey().intValue();
            }
            book.setId(newId);
            log.debug(String.format("Create a new book with id '%s'", newId));
            return book;

        } catch (DuplicateKeyException e) {
            log.error(String.format("Book '%s' is already exist", book.getTitle()));
            throw new SQLException("Internal sql exception");
        }
    }

    @Override
    public Book getById(Integer id) throws SQLException {

        String sql_query = env.getProperty("book.read");

        List<Book> books = template.query(sql_query, bookMapper, id);
        if (books.isEmpty()) {
            log.debug(String.format("Dont find any book by id '%s'", id));
            return null;
        } else if (books.size() == 1) {
            log.debug(String.format("Find a book by id '%s'", id));
            return books.get(0);
        } else {
            log.error(String.format("Find more than one book by id '%s'", id));
            throw new SQLException("Internal sql exception");
        }
    }

    @Override
    public void update(Book book) throws SQLException {

        String sql_query = env.getProperty("book.update");

        long id = book.getId();
        int recordCount = template.update(sql_query,
                book.getTitle(),
                book.getDescription(),
                book.getRelease_date(),
                book.getBook_language(),
                book.getPhoto(),
                id);
        if (recordCount == 0) {
            log.debug(String.format("Dont update any book by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Update book by id '%d'", id));
        } else {
            log.error(String.format("Update more than one book by id '%d'", id));
            throw new SQLException("Internal sql exception");
        }
    }

    @Override
    public void delete(Book book) throws SQLException {

        String sql_query = env.getProperty("book.delete");

        long id = book.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Dont delete any book by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Delete book by id '%d'", id));
        } else {
            log.error(String.format("Delete more than one book by id '%d'", id));
            throw new SQLException("Internal sql exception");
        }
    }

    @Override
    public Collection<Book> getAll() {

        String sql_query = env.getProperty("book.readAll");

        List<Book> books = template.query(sql_query, bookMapper);
        if (books.isEmpty()) {
            log.debug("Dont find any book");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d book(s)", books.size()));
            return books;
        }
    }

    @Override
    public Collection<Book> findBooksByGenre(int genre_id, int amount, int offset) {

        String sql_query = env.getProperty("book.findBooksByGenre");

        List<Book> books = template.query(sql_query, bookMapper, genre_id, amount, offset);
        if (books.isEmpty()) {
            log.debug(String.format("Dont find any book by genreId '%d'", genre_id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d book(s) by genreId '%d'", books.size(), genre_id));
            return books;
        }
    }

    @Override
    public Collection<Book> findBooksByAuthor(int author_id, int amount, int offset) {

        String sql_query = env.getProperty("book.findBooksByAuthor");

        List<Book> books = template.query(sql_query, bookMapper, author_id, amount, offset);
        if (books.isEmpty()) {
            log.debug(String.format("Dont find any book by authorID '%d'", author_id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d book(s) by authorID '%d'", books.size(), author_id));
            return books;
        }
    }

    @Override
    public Collection<Book> getById(int amount, int offset) {

        String sql_query = env.getProperty("book.getById");

        List<Book> books = template.query(sql_query, bookMapper, amount, offset);
        if (books.isEmpty()) {
            log.debug(String.format("Dont find any book with offset '%d'", offset));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d book(s) with offset '%d'", books.size(), offset));
            return books;
        }
    }

    @Override
    public Collection<Book> getByName(String name, int amount, int offset) throws SQLException {

        String sql_query = env.getProperty("book.getByName");

        List<Book> books = template.query(sql_query, bookMapper, name, name, amount, offset);
        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any books with name like '%s'", name));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) with name like '%s'", books.size(), name));
            return books;
        }
    }

    @Override
    public Collection<Book> getByUsername(String username, int amount, int offset) throws SQLException {
        String sql_query = env.getProperty("book.getByUsername");

        List<Book> books = template.query(sql_query, bookMapper, username, amount, offset);
        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any books for user '%s'", username));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) for user '%s'", books.size(), username));
            return books;
        }
    }

    @Override
    public Integer getCount() throws SQLException {
        String sql_query = env.getProperty("book.getCount");

        Integer count = template.queryForObject(sql_query, Integer.class);

        log.debug(String.format("Found %d books", count));
        return count;
    }

    @Override
    public Integer getCountByAuthor(int author_id) throws SQLException {
        String sql_query = env.getProperty("book.getCountByAuthor");

        Integer count = template.queryForObject(sql_query, new Object[]{author_id}, Integer.class);

        log.debug(String.format("Found %d books by authorID '%d' ", count, author_id));
        return count;
    }

    @Override
    public Integer getCountByGenre(int genre_id) throws SQLException {
        String sql_query = env.getProperty("book.getCountByGenre");

        Integer count = template.queryForObject(sql_query, new Object[]{genre_id}, Integer.class);

        log.debug(String.format("Found %d books by genreID '%d'", count, genre_id));
        return count;
    }

    @Override
    public Integer getCountByName(String name) throws SQLException {
        String sql_query = env.getProperty("book.getCountByName");

        Integer count = template.queryForObject(sql_query, new Object[]{name, name}, Integer.class);

        log.debug(String.format("Found %d books by name '%s'", count, name));
        return count;
    }

    @Override
    public Integer getCountByUsername(String username) throws SQLException {
        String sql_query = env.getProperty("book.getCountByUsername");

        Integer count = template.queryForObject(sql_query, new Object[] {"root"}, Integer.class);

        log.debug(String.format("Found %d books for user '%s'", count, username));
        return count;
    }
}
