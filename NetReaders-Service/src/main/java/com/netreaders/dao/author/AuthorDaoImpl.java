package com.netreaders.dao.author;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.DuplicateModelException;
import com.netreaders.exception.classes.NoSuchModelException;
import com.netreaders.models.Author;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@Log4j
@PropertySource("classpath:query.properties")
@AllArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

	private JdbcTemplate template;
    private final Environment env;
    private AuthorMapper authorMapper;

    @Override
    public Author create(Author author) {

        final String sql_query = env.getProperty("author.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateModelException if record exist in table
        try {
            template.update(creator(sql_query, author), holder);

            author.setId(retrieveId(holder));

            log.debug(String.format("Created a new author with id '%s'", author.getId()));
            return author;

        } catch (DuplicateKeyException e) {
            log.error(String.format("Author '%s' is already exist", author.getName()));
            throw new DuplicateModelException(String.format("Author '%s' is already exist", author.getName()));
        } catch (SQLException e) {
            log.error("Author creation fail!");
            throw new DataBaseSQLException("Author creation fail!");
        }
    }

    public Author getById(Integer id) {

        String sql_query = env.getProperty("author.read");

        List<Author> authors = template.query(sql_query, authorMapper, id);

        checkIfCollectionIsNull(authors);

        if (authors.isEmpty()) {
            log.error(String.format("Didn't find any author by id '%s'", id));
            throw new NoSuchModelException(String.format("Didn't find any author by id '%s'", id));
        } else if (authors.size() == 1) {
            log.debug(String.format("Found a author by id '%s'", id));
            return authors.get(0);
        } else {
            log.error(String.format("Found more than one author by id '%s'", id));
            throw new DataBaseSQLException(String.format("Found more than one author by id '%s'", id));
        }
    }

    @Override
    public void update(Author author) {

        String sql_query = env.getProperty("author.update");

        long id = author.getId();
        int recordCount = template.update(sql_query, author.getName(), id);
        if (recordCount == 0) {
            log.debug(String.format("Didn't update any author by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Updated author by id '%d'", id));
        } else {
            log.error(String.format("Updated more than one author by id '%d'", id));
            throw new DataBaseSQLException(String.format("Updated more than one author by id '%d'", id));
        }
    }

    @Override
    public void delete(Author author) {

        String sql_query = env.getProperty("author.delete");

        long id = author.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Didn't delete any author by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Deleted author by id '%d'", id));
        } else {
            log.error(String.format("Deleted more than one author by id '%d'", id));
            throw new DataBaseSQLException(String.format("Delete more than one author by id '%d'", id));
        }
    }

    public Collection<Author> getAll() {

        String sql_query = env.getProperty("author.readAll");

        List<Author> authors = template.query(sql_query, authorMapper);

        checkIfCollectionIsNull(authors);

        if (authors.isEmpty()) {
            log.debug("Didn't find any author");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d author(s)", authors.size()));
            return authors;
        }
    }

    public Collection<Author> getByBookId(int id) {

        String sql_query = env.getProperty("author.getByBookId");

        List<Author> authors = template.query(sql_query, authorMapper, id);

        checkIfCollectionIsNull(authors);

        if (authors.isEmpty()) {
            log.debug(String.format("Didn't find any author by bookID '%d'", id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d author(s) by bookID '%d'", authors.size(), id));
            return authors;
        }
    }

    private void checkIfCollectionIsNull(Collection<Author> collection) {
        if (collection == null) {
            // unreachable, but who knows (:
            log.error("Get `null` reference from jdbcTemplate");
            throw new DataBaseSQLException("Get `null` reference from jdbcTemplate");
        }
    }

    private PreparedStatementCreator creator(String sql, Author author) throws SQLException {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
        factory.setReturnGeneratedKeys(true);
        factory.addParameter(new SqlParameter(Types.VARCHAR));

        return factory.newPreparedStatementCreator(Collections.singletonList(author.getName()));
    }

    private Integer retrieveId(KeyHolder holder) {

        if (holder.getKeys() != null && holder.getKeys().size() > 0) {
            return (Integer) holder.getKeys().get("author_id");
        } else {
            return holder.getKey().intValue();
        }
    }
}
