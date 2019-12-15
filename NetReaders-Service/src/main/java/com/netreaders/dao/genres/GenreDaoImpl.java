package com.netreaders.dao.genres;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.NoSuchModelException;
import com.netreaders.models.Genre;
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
public class GenreDaoImpl implements GenreDao {

    private final Environment env;
    private final JdbcTemplate template;
    private final GenreMapper genreMapper;

    @Override
    public Genre create(Genre genre) {
        final String sql_query = env.getProperty("genre.create");

        KeyHolder holder = new GeneratedKeyHolder();

        try {
            template.update(creator(sql_query, genre), holder);

            genre.setId(retrieveId(holder));

            log.debug(String.format("Create a new genre with id '%s'", genre.getId()));
            return genre;

        } catch (DuplicateKeyException e) {
            log.error(String.format("Genre '%s' is already exist", genre.getName()));
            throw new DataBaseSQLException(String.format("Genre '%s' is already exist", genre.getName()));
        } catch (SQLException e) {
            log.error("Genre creation fail!");
            throw new DataBaseSQLException("Genre creation fail!");
        }
    }

    public Genre getById(Integer id) {

        final String sql_query = env.getProperty("genre.read");

        List<Genre> genres = template.query(sql_query, genreMapper, id);

        checkIfCollectionIsNull(genres);

        if (genres.isEmpty()) {
            log.error(String.format("Dont find any genre by id '%s'", id));
            throw new NoSuchModelException(String.format("Dont find any genre by id '%s'", id));
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

        final String sql_query = env.getProperty("genre.update");
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

        final String sql_query = env.getProperty("genre.delete");

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

        final String sql_query = env.getProperty("genre.readAll");

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

    public Collection<Genre> getByBookId(Integer id) {

        final String sql_query = env.getProperty("genre.getByBookId");

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

    private PreparedStatementCreator creator(String sql, Genre genre) throws SQLException {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
        factory.setReturnGeneratedKeys(true);
        factory.addParameter(new SqlParameter(Types.VARCHAR));

        return factory.newPreparedStatementCreator(Collections.singletonList(genre.getName()));
    }

    private Integer retrieveId(KeyHolder holder) throws SQLException {

        if (holder.getKeys() != null && holder.getKeys().size() > 0) {
            return (Integer) holder.getKeys().get("genre_id");
        } else {
            return holder.getKey().intValue();
        }
    }

	@Override
	public boolean existsByName(String name) throws DataBaseSQLException {
		 final String sql_query = env.getProperty("genre.getByName");
		 
		 List<Genre> result = template.query(sql_query,genreMapper,name);
		 
		 if (result.isEmpty()) {
	            log.debug(String.format("Didn't find any genre by name '%s'", name));
	            return false;
	     } else if (result.size() == 1) {
	            log.debug(String.format("Found a genre by name '%s'", name));
	            return true;
	     } else {
	            log.error(String.format("Found more than one genre by name '%s'", name));
	            throw new DataBaseSQLException(String.format("Found more than one genre by name '%s'", name));
	     }
	}
}
