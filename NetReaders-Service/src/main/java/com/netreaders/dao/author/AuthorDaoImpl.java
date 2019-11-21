package com.netreaders.dao.author;

import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Author;
import lombok.extern.log4j.Log4j;
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
import java.util.Collections;
import java.util.List;

@Log4j
@PropertySource("classpath:query.properties")
@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate template;

    private final Environment env;

    private final AuthorMapper authorMapper;

    public AuthorDaoImpl(JdbcTemplate template, Environment env, AuthorMapper authorMapper) {
        this.template = template;
        this.env = env;
        this.authorMapper = authorMapper;
    }

    @Override
    public Author create(Author author) {

        final String sql_query = env.getProperty("author.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateKeyException if record exist in table
        try {
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, author.getName());
                    return ps;
                }
            }, holder);

            Integer newId;
            if (holder.getKeys() != null && holder.getKeys().size() > 1) {
                newId = (Integer) holder.getKeys().get("author_id");
            } else {
                newId = holder.getKey().intValue();
            }
            author.setId(newId);
            log.debug(String.format("Create a new author with id '%s'", newId));

            return author;

        } catch (DuplicateKeyException e) {
            log.error(String.format("Author '%s' is already exist", author.getName()));
            throw new DataBaseSQLException(String.format("Author '%s' is already exist", author.getName()));
        }
    }

    public Author getById(Integer id) {

        String sql_query = env.getProperty("author.read");

        List<Author> authors = template.query(sql_query, authorMapper, id);

        checkIfCollectionIsNull(authors);

        if (authors.isEmpty()) {
            log.debug(String.format("Dont find any author by id '%s'", id));
            return null;
        } else if (authors.size() == 1) {
            log.debug(String.format("Find a author by id '%s'", id));
            return authors.get(0);
        } else {
            log.error(String.format("Find more than one author by id '%s'", id));
            throw new DataBaseSQLException(String.format("Find more than one author by id '%s'", id));
        }
    }

    @Override
    public void update(Author author) {

        String sql_query = env.getProperty("author.update");

        long id = author.getId();
        int recordCount = template.update(sql_query, author.getName(), id);
        if (recordCount == 0) {
            log.debug(String.format("Dont update any author by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Update author by id '%d'", id));
        } else {
            log.error(String.format("Update more than one author by id '%d'", id));
            throw new DataBaseSQLException(String.format("Update more than one author by id '%d'", id));
        }
    }

    @Override
    public void delete(Author author) {

        String sql_query = env.getProperty("author.delete");

        long id = author.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Dont delete any author by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Delete author by id '%d'", id));
        } else {
            log.error(String.format("Delete more than one author by id '%d'", id));
            throw new DataBaseSQLException(String.format("Delete more than one author by id '%d'", id));
        }
    }

    public Collection<Author> getAll() {

        String sql_query = env.getProperty("author.readAll");

        List<Author> authors = template.query(sql_query, authorMapper);

        checkIfCollectionIsNull(authors);

        if (authors.isEmpty()) {
            log.debug("Dont find any author");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d author(s)", authors.size()));
            return authors;
        }
    }

    public Collection<Author> getByBookId(int id) {

        String sql_query = env.getProperty("author.getByBookId");

        List<Author> authors = template.query(sql_query, authorMapper, id);

        checkIfCollectionIsNull(authors);

        if (authors.isEmpty()) {
            log.debug(String.format("Dont find any author by bookID '%d'", id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d author(s) by bookID '%d'", authors.size(), id));
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
}
