package com.netreaders.dao.book;

import com.netreaders.models.Book;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Log4j
@PropertySource("classpath:query.properties")
@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private Environment env;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book create(Book book) throws SQLException {

        final String sql_query = env.getProperty("book.create");

        KeyHolder holder = new GeneratedKeyHolder();

        try {
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, book.getTitle());
                    ps.setInt(2, book.getPhoto());
                    ps.setString(3, book.getDescription());
                    ps.setDate(4, book.getRelease_date());
                    ps.setString(5, book.getBook_language());
                    return ps;
                }
            }, holder);

            Integer newId;
            if (holder.getKeys().size() > 1) {
                newId = (Integer) holder.getKeys().get("book_id");
            } else {
                newId = holder.getKey().intValue();
            }
            book.setId(newId);
            log.info(String.format("Create a new book with id '%s'", newId));
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
            log.info(String.format("Dont find any book by id '%s'", id));
            return null;
        } else if (books.size() == 1) {
            log.info(String.format("Find a book by id '%s'", id));
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
            log.info(String.format("Dont update any book by id '%d'", id));
        } else if (recordCount == 1) {
            log.info(String.format("Update book by id '%d'", id));
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
            log.info(String.format("Dont delete any book by id '%d'", id));
        } else if (recordCount == 1) {
            log.info(String.format("Delete book by id '%d'", id));
        } else {
            log.error(String.format("Delete more than one book by id '%d'", id));
            throw new SQLException("Internal sql exception");
        }
    }

    @Override
    public Collection<Book> getAll() throws SQLException {

        String sql_query = env.getProperty("book.readAll");

        List<Book> books = template.query(sql_query, bookMapper);

        if (books.isEmpty()) {
            log.info("Dont find any book");
            return null;
        } else {
            log.info(String.format("Find %d book(s)", books.size()));
            return books;
        }
    }

    public Collection<Book> findBooksByGenre(int genre_id, int amount, int offset) {

        String sql_query = env.getProperty("book.findBooksByGenre");

        List<Book> books = template.query(sql_query, bookMapper, genre_id, amount, offset);

        if (books.isEmpty()) {
            log.info(String.format("Dont find any book by genreId '%d'", genre_id));
            return null;
        } else {
            log.info(String.format("Find %d book(s) by genreId '%d'", books.size(), genre_id));
            return books;
        }
    }

    public Collection<Book> findBooksByAuthor(int author_id, int amount, int offset) {

        String sql_query = env.getProperty("book.findBooksByAuthor");

        List<Book> books = template.query(sql_query, bookMapper, author_id, amount, offset);

        if (books.isEmpty()) {
            log.info(String.format("Dont find any book by authorID '%d'", author_id));
            return null;
        } else {
            log.info(String.format("Find %d book(s) by authorID '%d'", books.size(), author_id));
            return books;
        }
    }

    public Collection<Book> getById(int amount, int offset) {

        String sql_query = env.getProperty("book.getByIdWithOffset");

        List<Book> books = template.query(sql_query, bookMapper, amount, offset);

        if (books.isEmpty()) {
            log.info(String.format("Dont find any book with offset '%d'", offset));
            return null;
        } else {
            log.info(String.format("Find %d book(s) with offset '%d'", books.size(), offset));
            return books;
        }
    }

	@Override
	public Collection<Book> getByName(String name) throws SQLException {
		
		String sql_query = env.getProperty("book.getByName");
		
		List<Book> books = template.query(sql_query, bookMapper, name);
		
		if (books.isEmpty()) {
            log.info(String.format("Didn't find any books with name like '%s'", name));
            return null;
        } else {
            log.info(String.format("Found %d book(s) with name like '%s'", books.size(), name));
            return books;
        }
	}
}
