package com.netreaders.dao.genres;

import com.netreaders.exception.DataBaseSQLException;
import com.netreaders.models.Genre;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Log4j
@PropertySource("classpath:query.properties")
@Repository
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate template;

    private final Environment env;

    private final GenreMapper genreMapper;

    public GenreDaoImpl(JdbcTemplate template, Environment env, GenreMapper genreMapper) {
        this.template = template;
        this.env = env;
        this.genreMapper = genreMapper;
    }

    @Override
    public Genre create(Genre genre) {

        final String sql_query = env.getProperty("genre.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateKeyException if record exist in table
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, genre.getName());
                return ps;
            }, holder);

            Integer newId;
            if (holder.getKeys() != null && holder.getKeys().size() > 1) {
                newId = (Integer) holder.getKeys().get("genre_id");
            } else {
                newId = holder.getKey().intValue();
            }
            genre.setId(newId);
            log.debug(String.format("Create a new genre with id '%s'", newId));

            return genre;

        } catch (DuplicateKeyException e) {
            log.error(String.format("Genre '%s' is already exist", genre.getName()));
            throw new DataBaseSQLException(String.format("Genre '%s' is already exist", genre.getName()));
        }
    }

    public Genre getById(Integer id) {

        String sql_query = env.getProperty("genre.read");

        List<Genre> genres = template.query(sql_query, genreMapper, id);

        checkIfCollectionIsNull(genres);

        if (genres.isEmpty()) {
            log.debug(String.format("Dont find any genre by id '%s'", id));
            return null;
        } else if (genres.size() == 1) {
            log.debug(String.format("Find a genre by id '%s'", id));
            return genres.get(0);
        } else {
            log.error(String.format("Find more than one genre by id '%s'", id));
            throw new DataBaseSQLException(String.format("Find more than one genre by id '%s'", id));
        }
    }

    @Override
    public void update(Genre genre) {

        String sql_query = env.getProperty("genre.update");
        long id = genre.getId();

        int recordCount = template.update(sql_query, genre.getName(), id);

        if (recordCount == 0) {
            log.debug(String.format("Dont update any genre by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Update genre by id '%d'", id));
        } else {
            log.error(String.format("Update more than one genre by id '%d'", id));
            throw new DataBaseSQLException(String.format("Update more than one genre by id '%d'", id));
        }
    }

    @Override
    public void delete(Genre genre) {

        String sql_query = env.getProperty("genre.delete");

        long id = genre.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Dont delete any genre by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Delete genre by id '%d'", id));
        } else {
            log.error(String.format("Delete more than one genre by id '%d'", id));
            throw new DataBaseSQLException(String.format("Delete more than one genre by id '%d'", id));
        }

    }

    public Collection<Genre> getAll() {

        String sql_query = env.getProperty("genre.readAll");

        List<Genre> genres = template.query(sql_query, genreMapper);

        checkIfCollectionIsNull(genres);

        if (genres.isEmpty()) {
            log.debug("Dont find any genre");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d genre(s)", genres.size()));
            return genres;
        }
    }

    public Collection<Genre> getByBookId(int id) {

        String sql_query = env.getProperty("genre.getByBookId");

        List<Genre> books = template.query(sql_query, genreMapper, id);

        checkIfCollectionIsNull(books);

        if (books.isEmpty()) {
            log.debug(String.format("Dont find any genre by bookID '%d'", id));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d genre(s) by bookID '%d'", books.size(), id));
            return books;
        }
    }

    private void checkIfCollectionIsNull(Collection<Genre> collection) {
        if (collection == null) {
            // unreachable, but who knows (:
            log.error("Get `null` reference from jdbcTemplate");
            throw new DataBaseSQLException("Get `null` reference from jdbcTemplate");
        }
    }
}
