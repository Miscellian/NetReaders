package com.netreaders.dao.book;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.DuplicateModelException;
import com.netreaders.exception.classes.NoSuchModelException;
import com.netreaders.models.Book;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@Log4j
@PropertySource("classpath:query.properties")
@AllArgsConstructor
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate template;
    private final Environment env;
    private final BookMapper bookMapper;
    @Override
    public Book create(Book book) {

        final String sql_query = env.getProperty("book.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateKeyException if record exist in table
        try {
            template.update(creator(sql_query, book), holder);

            book.setId(retrieveId(holder));

            log.debug(String.format("Created a new book with id '%s'", book.getId()));
            return book;
        } catch (DuplicateKeyException e) {
            log.error(String.format("Book '%s' is already exist", book.getTitle()));
            throw new DuplicateModelException(String.format("Book '%s' is already exist", book.getTitle()));
        } catch (SQLException e) {
            log.error("Book creation fail!");
            throw new DataBaseSQLException("Book creation fail!");
        }
    }

    @Override
    public Book getById(Integer id) {

        String sql_query = env.getProperty("book.read");

        List<Book> books = template.query(sql_query, bookMapper, id);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any book by id '%s'", id));
            throw new NoSuchModelException(String.format("Didn't find any book by id '%s'", id));
        } else if (books.size() == 1) {
            log.debug(String.format("Found a book by id '%s'", id));
            return books.get(0);
        } else {
            log.error(String.format("Found more than one book by id '%s'", id));
            throw new DataBaseSQLException(String.format("Found more than one book by id '%s'", id));
        }
    }

    @Override
    public void update(Book book) {

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
            log.debug(String.format("Didn't update any book by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Updated book by id '%d'", id));
        } else {
            log.error(String.format("Updated more than one book by id '%d'", id));
            throw new DataBaseSQLException(String.format("Updated more than one book by id '%d'", id));
        }
    }

    @Override
    public void delete(Book book) {

        String sql_query = env.getProperty("book.delete");

        long id = book.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Didn't delete any book by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Deleted book by id '%d'", id));
        } else {
            log.error(String.format("Deleted more than one book by id '%d'", id));
            throw new DataBaseSQLException(String.format("Deleted more than one book by id '%d'", id));
        }
    }

    @Override
    public Collection<Book> getAll() {

        String sql_query = env.getProperty("book.readAll");

        List<Book> books = template.query(sql_query, bookMapper);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug("Didn't find any book");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s)", books.size()));
            return books;
        }
    }

    public Collection<Book> findByGenre(int genre_id, int amount, int offset) {

        String sql_query = env.getProperty("book.findBooksByGenre");

        List<Book> books = template.query(sql_query, bookMapper, genre_id, amount, offset);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any book by genreId '%d'", genre_id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) by genreId '%d'", books.size(), genre_id));
            return books;
        }
    }

    public Collection<Book> findByAuthor(int author_id, int amount, int offset) {

        String sql_query = env.getProperty("book.findBooksByAuthor");

        List<Book> books = template.query(sql_query, bookMapper, author_id, amount, offset);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any book by authorID '%d'", author_id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) by authorID '%d'", books.size(), author_id));
            return books;
        }
    }

    public Collection<Book> findAll(int amount, int offset) throws DataBaseSQLException {
        String sql_query = env.getProperty("book.getByIdWithOffset");

        List<Book> books = template.query(sql_query, bookMapper, amount, offset);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any book with offset '%d'", offset));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) with offset '%d'", books.size(), offset));
            return books;
        }
    }

    @Override
    public Collection<Book> findByName(String name, int amount, int offset) {

        String sql_query = env.getProperty("book.getByNameWithOffset");

        List<Book> books = template.query(sql_query, bookMapper, name, name, amount, offset);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any books with name like '%s'", name));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) with name like '%s'", books.size(), name));
            return books;
        }
    }

    @Override
    public Integer getCount() {
        String sql_query = env.getProperty("book.getCount");

        Integer count = template.queryForObject(sql_query, Integer.class);

        log.debug(String.format("Found %d books", count));
        return count;
    }

    @Override
    public Integer getCountByAuthor(int author_id) {
        String sql_query = env.getProperty("book.getCountByAuthor");

        Integer count = template.queryForObject(sql_query, new Object[]{author_id}, Integer.class);

        log.debug(String.format("Found %d books by authorID '%d' ", count, author_id));
        return count;
    }

    @Override
    public Integer getCountByGenre(int genre_id) {
        String sql_query = env.getProperty("book.getCountByGenre");

        Integer count = template.queryForObject(sql_query, new Object[]{genre_id}, Integer.class);

        log.debug(String.format("Found %d books by genreID '%d'", count, genre_id));
        return count;
    }

    @Override
    public Integer getCountByName(String name) {
        String sql_query = env.getProperty("book.getCountByName");

        Integer count = template.queryForObject(sql_query, new Object[]{name, name}, Integer.class);

        log.debug(String.format("Found %d books by name '%s'", count, name));
        return count;
    }

    @Override
    public Collection<Book> findByAnnouncementId(Integer announcementId) {

        String sql_query = env.getProperty("book.findByAnnouncementId");

        List<Book> books = template.query(sql_query, bookMapper, announcementId);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any book by announcementID '%d'", announcementId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) by announcementID '%d'", books.size(), announcementId));
            return books;
        }
    }

    @Override
    public Collection<Book> findByAnnouncementWithGenre(Integer announcementId, Integer genreId) {

        String sql_query = env.getProperty("book.findByAnnouncementIdWithGenre");

        List<Book> books = template.query(sql_query, bookMapper, announcementId, genreId);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any book by announcementID '%d' with genreID '%d'", announcementId, genreId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) by announcementID '%d' with genreID '%d'", books.size(), announcementId, genreId));
            return books;
        }
    }

    @Override
    public Collection<Book> findByAnnouncementWithAuthor(Integer announcementId, Integer authorId) {

        String sql_query = env.getProperty("book.findByAnnouncementIdWithAuthor");

        List<Book> books = template.query(sql_query, bookMapper, announcementId, authorId);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any book by announcementID '%d' with authorID '%d'", announcementId, authorId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) by announcementID '%d' with authorID '%d'", books.size(), announcementId, authorId));
            return books;
        }
    }

    private void checkIfCollectionIsNull(Collection<Book> collection) {
        if (collection == null) {
            // unreachable, but who knows (:
            log.error("Get `null` reference from jdbcTemplate");
            throw new DataBaseSQLException("Get `null` reference from jdbcTemplate");
        }
    }

    private PreparedStatementCreator creator(String sql, Book book) throws SQLException {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
        factory.setReturnGeneratedKeys(true);
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.INTEGER));
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.DATE));
        factory.addParameter(new SqlParameter(Types.VARCHAR));

        return factory.newPreparedStatementCreator(Arrays.asList(
                book.getTitle(),
                book.getPhoto(),
                book.getDescription(),
                book.getRelease_date(),
                book.getBook_language()));
    }

    private Integer retrieveId(KeyHolder holder) {

        if (holder.getKeys() != null && holder.getKeys().size() > 0) {
            return (Integer) holder.getKeys().get("book_id");
        } else {
            return holder.getKey().intValue();
        }
    }

	@Override
	public Book getByReviewId(int id) throws SQLException {
		String sql_query = env.getProperty("book.getByReviewId");

        List<Book> books = template.query(sql_query, bookMapper, id);
        if (books.isEmpty()) {
            log.debug(String.format("Dont find any book by review_id '%d'", id));
            return null;
        } else if (books.size() == 1) {
            log.debug(String.format("Find a book by review_id '%sd'", id));
            return books.get(0);
        } else {
            log.error(String.format("Find more than one book by review_id '%s'", id));
            throw new SQLException("Internal sql exception");
        }
	}
	@Override
	public Integer getCountByUsername(String username) throws SQLException {
		String sql_query = env.getProperty("book.getCountByUsername");

        Integer count = template.queryForObject(sql_query, new Object[] {"root"}, Integer.class);

        log.debug(String.format("Found %d books for user '%s'", count, username));
        return count;
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
	public Collection<Book> getById(int amount, int offset) throws SQLException {
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
}
