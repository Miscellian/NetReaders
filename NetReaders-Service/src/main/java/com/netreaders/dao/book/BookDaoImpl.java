package com.netreaders.dao.book;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.NoSuchModelException;
import com.netreaders.models.Book;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
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

    private final Environment env;
    private final JdbcTemplate template;
    private final BookMapper bookMapper;
    private final KeyHolder holder;

    @Override
    public Book create(Book book) {

        final String sql_query = env.getProperty("book.create");


        try {
            template.update(creator(sql_query, book), holder);

            book.setId(retrieveId(holder));

            log.debug(String.format("Created a new book with id '%s'", book.getId()));
            return book;

        } catch (SQLException e) {
            log.error("Book creation fail!");
            throw new DataBaseSQLException("Book creation fail!");
        }
    }

    @Override
    public Book getById(Integer id) {
        final String sql_query = env.getProperty("book.read");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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

        final String sql_query = env.getProperty("book.update");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        long id = book.getId();
        int recordCount = template.update(sql_query,
                book.getTitle(),
                book.getDescription(),
                book.getRelease_date(),
                book.getBook_language(),
                book.getPhoto(),
                book.isPublished(),
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

        final String sql_query = env.getProperty("book.delete");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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

        final String sql_query = env.getProperty("book.readAll");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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

    @Override
    public Collection<Book> findByFavouritesUsername(String username, Integer amount, Integer offset) {
        final String sql_query = env.getProperty("book.getFavouritesByUsername");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        List<Book> books = template.query(sql_query, bookMapper, username, amount, offset);
        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any favourites for user '%s'", username));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d favourite(s) for user '%s'", books.size(), username));
            return books;
        }
    }


    @Override
    public Collection<Book> findToReadListByUsername(String username, Integer amount, Integer offset) {
        final String sql_query = env.getProperty("book.getToReadListByUsername");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        List<Book> books = template.query(sql_query, bookMapper, username, amount, offset);
        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any to read books for user '%s'", username));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d to read book(s) for user '%s'", books.size(), username));
            return books;
        }
    }

    public Collection<Book> findByGenre(Integer genre_id, Integer amount, Integer offset) {

        final String sql_query = env.getProperty("book.findBooksByGenre");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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

    public Collection<Book> findByAuthor(Integer authorId, Integer amount, Integer offset) {

        final String sql_query = env.getProperty("book.findBooksByAuthor");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        List<Book> books = template.query(sql_query, bookMapper, authorId, amount, offset);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any book by authorID '%d'", authorId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d book(s) by authorID '%d'", books.size(), authorId));
            return books;
        }
    }

    public Collection<Book> findByUsername(String username, Integer amount, Integer offset) {
        final String sql_query = env.getProperty("book.getByUsername");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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
    public Collection<Book> findByName(String name, Integer amount, Integer offset) {

        final String sql_query = env.getProperty("book.getByName");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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

    public Collection<Book> findAll(Integer amount, Integer offset) throws DataBaseSQLException {

        final String sql_query = env.getProperty("book.getById");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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
    public Integer getCount() {

        final String sql_query = env.getProperty("book.getCount");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        Integer count = template.queryForObject(sql_query, Integer.class);

        log.debug(String.format("Found %d books", count));
        return count;
    }

    @Override
    public Integer getCountByAuthor(Integer author_id) {
        String sql_query = env.getProperty("book.getCountByAuthor");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        Integer count = template.queryForObject(sql_query, new Object[]{author_id}, Integer.class);

        log.debug(String.format("Found %d books by authorID '%d' ", count, author_id));
        return count;
    }

    @Override
    public Integer getCountByGenre(Integer genreId) {
        final String sql_query = env.getProperty("book.getCountByGenre");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        Integer count = template.queryForObject(sql_query, new Object[]{genreId}, Integer.class);

        log.debug(String.format("Found %d books by genreID '%d'", count, genreId));
        return count;
    }

    @Override
    public Integer getCountByName(String name) {
        String sql_query = env.getProperty("book.getCountByName");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        Integer count = template.queryForObject(sql_query, new Object[]{name, name}, Integer.class);

        log.debug(String.format("Found %d books by name '%s'", count, name));
        return count;
    }

    @Override
    public Integer getCountByUsername(String username) {
        String sql_query = env.getProperty("book.getCountByUsername");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        Integer count = template.queryForObject(sql_query, new Object[]{username}, Integer.class);

        log.debug(String.format("Found %d books by username '%s'", count, username));
        return count;
    }

    @Override
    public Integer getFavouritesCountByUsername(String username) {
        String sql_query = env.getProperty("book.getFavouritesCountByUsername");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        Integer count = template.queryForObject(sql_query, new Object[]{username}, Integer.class);

        log.debug(String.format("Found %d favourites by username '%s'", count, username));
        return count;
    }

    @Override
    public Integer getToReadListCountByUsername(String username) {
        String sql_query = env.getProperty("book.getToReadListCountByUsername");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        Integer count = template.queryForObject(sql_query, new Object[]{username}, Integer.class);

        log.debug(String.format("Found %d favourites by username '%s'", count, username));
        return count;
    }

    @Override
    public Collection<Book> findByAnnouncementId(Integer announcementId) {
        final String sql_query = env.getProperty("book.findByAnnouncementId");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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

        final String sql_query = env.getProperty("book.findByAnnouncementIdWithGenre");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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

        final String sql_query = env.getProperty("book.findByAnnouncementIdWithAuthor");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

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

    @Override
    public Book getByReviewId(int id) throws DataBaseSQLException {
        final String sql_query = env.getProperty("book.getByReviewId");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        List<Book> books = template.query(sql_query, bookMapper, id);
        if (books.isEmpty()) {
            log.debug(String.format("Dont find any book by review_id '%d'", id));
            return null;
        } else if (books.size() == 1) {
            log.debug(String.format("Find a book by review_id '%sd'", id));
            return books.get(0);
        } else {
            log.error(String.format("Find more than one book by review_id '%s'", id));
            throw new DataBaseSQLException(String.format("Find more than one book by review_id '%s'", id));
        }
    }

    @Override
    public void addBookToUserLibrary(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.addBookToUserLibrary");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        template.update(sql_query, username, bookId);

        log.debug(String.format("Add a book with id %d to %s library", bookId, username));
    }

    @Override
    public boolean checkIfBookInUserLibrary(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.checkIfBookInUserLibrary");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        List<Book> books = template.query(sql_query, bookMapper, username, bookId);
        if (books.isEmpty()) {
            log.debug(String.format("Book with id %d not in %s library", bookId, username));
            return false;
        } else if (books.size() == 1) {
            log.debug(String.format("Book with id %d already in %s library", bookId, username));
            return true;
        } else {
            log.error(String.format("Find more than one book with id %d for user %s", bookId, username));
            throw new DataBaseSQLException(String.format("Find more than one book with id %d for user %s", bookId, username));
        }
    }

    @Override
    public void removeBookFromUserLibrary(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.removeBookFromUserLibrary");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        template.update(sql_query, username, bookId);

        log.debug(String.format("Remove a book with id %d to %s library", bookId, username));
    }

    @Override
    public boolean checkIfBookInFavourites(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.checkIfBookInFavourites");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        List<Book> books = template.query(sql_query, bookMapper, username, bookId);
        if (books.isEmpty()) {
            log.debug(String.format("Book with id %d not in %s favourites", bookId, username));
            return false;
        } else if (books.size() == 1) {
            log.debug(String.format("Book with id %d already in %s favourites", bookId, username));
            return true;
        } else {
            log.error(String.format("Find more than one favourite book with id %d for user %s", bookId, username));
            throw new DataBaseSQLException(String.format("Find more than one favourite book with id %d for user %s", bookId, username));
        }
    }

    @Override
    public void addBookToUserFavourites(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.addBookToUserFavourites");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        template.update(sql_query, username, bookId);

        log.debug(String.format("Add a book with id %d to %s favourites", bookId, username));
    }

    @Override
    public void removeBookToUserFavourites(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.removeBookToUserFavourites");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        template.update(sql_query, username, bookId);

        log.debug(String.format("Remove a book with id %d from %s favourites", bookId, username));
    }

    @Override
    public boolean checkIfBookInUserToReadList(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.checkIfBookInToReadList");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        List<Book> books = template.query(sql_query, bookMapper, username, bookId);
        if (books.isEmpty()) {
            log.debug(String.format("Book with id %d not in %s to read list", bookId, username));
            return false;
        } else if (books.size() == 1) {
            log.debug(String.format("Book with id %d already in %s to read list", bookId, username));
            return true;
        } else {
            log.error(String.format("Find more than one to read book with id %d for user %s", bookId, username));
            throw new DataBaseSQLException(String.format("Find more than one to read book with id %d for user %s", bookId, username));
        }
    }

    @Override
    public void addBookToUserToReadList(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.addBookToUserToReadList");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        template.update(sql_query, username, bookId);

        log.debug(String.format("Add a book with id %d to %s to read list", bookId, username));
    }

    @Override
    public void removeBookFromUserToReadList(String username, Integer bookId) {
        final String sql_query = env.getProperty("book.removeBookToUserToReadList");

        if (sql_query == null) {
            log.debug("Sql query is null");
            throw new DataBaseSQLException("Sql query is null");
        }

        template.update(sql_query, username, bookId);

        log.debug(String.format("Remove a book with id %d from %s to read list", bookId, username));
    }

    @Override
    public boolean checkBookExistsByTitle(String title) {
        final String sql_query = env.getProperty("book.getByTitle");

        List<Book> result = template.query(sql_query, bookMapper, title);

        if (result.isEmpty()) {
            log.debug(String.format("Didn't find any books by title '%s'", title));
            return false;
        } else if (result.size() == 1) {
            log.debug(String.format("Found a book by title '%s'", title));
            return true;
        } else {
            log.error(String.format("Found more than one book by title '%s'", title));
            throw new DataBaseSQLException(String.format("Found more than one book by title '%s'", title));
        }
    }


    @Override
    public Collection<Book> getUnpublished(Integer amount, Integer offset) throws DataBaseSQLException {
        final String sql_query = env.getProperty("book.getUnpublished");

        List<Book> books = template.query(sql_query, bookMapper, amount, offset);

        if (books.isEmpty()) {
            log.debug(String.format("Didn't find any unpublished books "));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d unpublished book(s) by amount '%d' and offset '%d'", books.size(), amount, offset));
            return books;
        }
    }


    @Override
    public Integer getUnpublishedCount() throws DataBaseSQLException {
        String sql_query = env.getProperty("book.getUnpublishedCount");

        Integer count = template.queryForObject(sql_query, Integer.class);

        log.debug(String.format("Found %d unpublished books", count));
        return count;
    }


    @Override
    public void addGenre(Integer bookId, Integer genreId) throws DataBaseSQLException {
        String sql_query = env.getProperty("book.addGenre");

        int result = template.update(sql_query, bookId, genreId);

        if (result == 0) {
            log.error(String.format("Couldn't insert genre '%d' to book '%d'", genreId, bookId));
            throw new DataBaseSQLException(String.format("Couldn't insert genre '%d' to book '%d'", genreId, bookId));
        }
        log.debug(String.format("Added genre '%d' to book '%d' ", genreId, bookId));
    }


    @Override
    public void addAuthor(Integer bookId, Integer authorId) throws DataBaseSQLException {
        String sql_query = env.getProperty("book.addAuthors");

        int result = template.update(sql_query, bookId, authorId);

        if (result == 0) {
            log.error(String.format("Couldn't insert author '%d' to book '%d'", authorId, bookId));
            throw new DataBaseSQLException(String.format("Couldn't insert author '%d' to book '%d'", authorId, bookId));
        }
        log.debug(String.format("Added author '%d' to book '%d' ", authorId, bookId));
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
        factory.addParameter(new SqlParameter(Types.BOOLEAN));

        return factory.newPreparedStatementCreator(Arrays.asList(
                book.getTitle(),
                book.getPhoto(),
                book.getDescription(),
                book.getRelease_date(),
                book.getBook_language(),
                book.isPublished()));
    }

    private Integer retrieveId(KeyHolder holder) {

        if (holder.getKeys() != null && holder.getKeys().size() > 0) {
            return (Integer) holder.getKeys().get("book_id");
        } else {
            return holder.getKey().intValue();
        }
    }
}
