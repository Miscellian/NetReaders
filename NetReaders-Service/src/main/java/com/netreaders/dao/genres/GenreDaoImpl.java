package com.netreaders.dao.genres;

import com.netreaders.models.Genre;
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
public class GenreDaoImpl implements GenreDao {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private Environment env;

    @Autowired
    private GenreMapper genreMapper;

    @Override
    public Genre create(Genre genre) throws SQLException {

        final String sql_query = env.getProperty("genre.create");

        KeyHolder holder = new GeneratedKeyHolder();

        try {
            template.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, genre.getName());
                    return ps;
                }
            }, holder);

            Integer newId;
            if (holder.getKeys().size() > 1) {
                newId = (Integer) holder.getKeys().get("genre_id");
            } else {
                newId = holder.getKey().intValue();
            }
            genre.setId(newId);
            log.info(String.format("Create a new genre with id '%s'", newId));

            return genre;

        } catch (DuplicateKeyException e) {
            log.error(String.format("Genre '%s' is already exist", genre.getName()));
            throw new SQLException("Internal sql exception");
        }
    }

    public Genre getById(Integer id) throws SQLException {

        String sql_query = env.getProperty("genre.read");

        List<Genre> genres = template.query(sql_query, genreMapper, id);

        if (genres.isEmpty()) {
            log.info(String.format("Dont find any genre by id '%s'", id));
            return null;
        } else if (genres.size() == 1) {
            log.info(String.format("Find a genre by id '%s'", id));
            return genres.get(0);
        } else {
            log.error(String.format("Find more than one genre by id '%s'", id));
            throw new SQLException("Internal sql exception");
        }
    }

    @Override
    public void update(Genre genre) throws SQLException {

        String sql_query = env.getProperty("genre.update");
        long id = genre.getId();

        int recordCount = template.update(sql_query, genre.getName(), id);

        if (recordCount == 0) {
            log.info(String.format("Dont update any genre by id '%d'", id));
        } else if (recordCount == 1) {
            log.info(String.format("Update genre by id '%d'", id));
        } else {
            log.error(String.format("Update more than one genre by id '%d'", id));
            throw new SQLException("Internal sql exception");
        }
    }

    @Override
    public void delete(Genre genre) throws SQLException {

        String sql_query = env.getProperty("genre.delete");

        long id = genre.getId();
        int recordCount = template.update(sql_query, id);

        if (recordCount == 0) {
            log.info(String.format("Dont delete any genre by id '%d'", id));
        } else if (recordCount == 1) {
            log.info(String.format("Delete genre by id '%d'", id));
        } else {
            log.error(String.format("Delete more than one genre by id '%d'", id));
            throw new SQLException("Internal sql exception");
        }

    }

    public Collection<Genre> getAll() throws SQLException {

        String sql_query = env.getProperty("genre.readAll");

        List<Genre> genres = template.query(sql_query, genreMapper);

        if (genres.isEmpty()) {
            log.info("Dont find any genre");
            return null;
        } else {
            log.info(String.format("Find %d genre(s)", genres.size()));
            return genres;
        }
    }

    public Collection<Genre> getByBookId(int id) throws SQLException {

        String sql_query = env.getProperty("genre.getByBookId");

        List<Genre> books = template.query(sql_query, genreMapper, id);

        if (books.isEmpty()) {
            log.info(String.format("Dont find any genre by bookID '%d'", id));
            return null;
        } else {
            log.info(String.format("Find %d genre(s) by bookID '%d'", books.size(), id));
            return books;
        }
    }
}
