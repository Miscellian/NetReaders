package com.netreaders.dao.review;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.DuplicateModelException;
import com.netreaders.models.Review;
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

@Log4j
@PropertySource("classpath:query.properties")
@AllArgsConstructor
@Repository
public class ReviewDaoImpl implements ReviewDao {

    private final JdbcTemplate template;
    private final Environment env;
    private final ReviewMapper reviewMapper;

    @Override
    public Review create(Review review) {

        final String sql_query = env.getProperty("review.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateKeyException if record exist in table
        try {
            template.update(creator(sql_query, review), holder);

            review.setId(retrieveId(holder));

            log.debug(String.format("Created a new review with id '%d'", review.getId()));
            return review;
        } catch (DuplicateKeyException e) {
            log.error(String.format("Review '%d' is already exist", review.getId()));
            throw new DuplicateModelException(String.format("Review '%d' is already exist", review.getId()));
        } catch (SQLException e) {
            log.error("Review creation fail!");
            throw new DataBaseSQLException("Review creation fail!");
        }
    }

    @Override
    public Review getById(Integer id) {

        final String sql_query = env.getProperty("review.read");

        List<Review> reviews = template.query(sql_query, reviewMapper, id);
        if (reviews.isEmpty()) {
            log.debug(String.format("Didn't find any review by id '%d'", id));
            return null;
        } else if (reviews.size() == 1) {
            log.debug(String.format("Found a review by id '%d'", id));
            return reviews.get(0);
        } else {
            log.error(String.format("Found more than one review by id '%d'", id));
            throw new DataBaseSQLException(String.format("Found more than one review by id '%d'", id));
        }
    }

    @Override
    public void update(Review review) {

        final String sql_query = env.getProperty("review.update");

        long id = review.getId();
        int recordCount = template.update(sql_query,
                review.getRating(),
                review.getDescription(),
                review.isPublished(),
                id);
        if (recordCount == 0) {
            log.debug(String.format("Didn't update any review by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Updated review by id '%d'", id));
        } else {
            log.error(String.format("Updated more than one review by id '%d'", id));
            throw new DataBaseSQLException(String.format("Updated more than one review by id '%d'", id));
        }
    }

    @Override
    public void delete(Review review) {

        final String sql_query = env.getProperty("review.delete");

        long id = review.getId();
        int recordCount = template.update(sql_query, id);
        if (recordCount == 0) {
            log.debug(String.format("Didn't delete any review by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Deleted review by id '%d'", id));
        } else {
            log.error(String.format("Deleted more than one review by id '%d'", id));
            throw new DataBaseSQLException(String.format("Deleted more than one review by id '%d'", id));
        }
    }

    @Override
    public Collection<Review> getAll() {

        final String sql_query = env.getProperty("review.readAll");

        List<Review> reviews = template.query(sql_query, reviewMapper);

        checkIfCollectionIsNull(reviews);

        if (reviews.isEmpty()) {
            log.debug("Didn't find any review");
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d review(s)", reviews.size()));
            return reviews;
        }
    }

    @Override
    public Collection<Review> getByBookId(Integer bookId, Integer amount, Integer offset) {

        final String sql_query = env.getProperty("review.getByBookId");

        List<Review> reviews = template.query(sql_query, reviewMapper, bookId, amount, offset);
        if (reviews.isEmpty()) {
            log.debug(String.format("Didn't find any review by bookID '%d'", bookId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d review(s) by bookID '%d'", reviews.size(), bookId));
            return reviews;
        }
    }

    public Collection<Review> getPublishedByBookId(Integer bookId, Integer amount, Integer offset) {

        final String sql_query = env.getProperty("review.getPublishedByBookId");

        List<Review> reviews = template.query(sql_query, reviewMapper, bookId, amount, offset);
        if (reviews.isEmpty()) {
            log.debug(String.format("Didn't find any  published review by bookID '%d'", bookId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d published review(s) by bookID '%d'", reviews.size(), bookId));
            return reviews;
        }
    }

    public Collection<Review> getUnpublishedByBookId(Integer bookId, Integer amount, Integer offset) {

        final String sql_query = env.getProperty("review.getUnpublishedByBookId");

        List<Review> reviews = template.query(sql_query, reviewMapper, bookId, amount, offset);
        if (reviews.isEmpty()) {
            log.debug(String.format("Didn't find any unpublished review by bookID '%d'", bookId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Found %d unpublished review(s) by bookID '%d'", reviews.size(), bookId));
            return reviews;
        }
    }

    private void checkIfCollectionIsNull(Collection<Review> collection) {
        if (collection == null) {
            // unreachable, but who knows (:
            log.error("Get `null` reference from jdbcTemplate");
            throw new DataBaseSQLException("Get `null` reference from jdbcTemplate");
        }
    }

    private PreparedStatementCreator creator(String sql, Review review) throws SQLException {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql);
        factory.setReturnGeneratedKeys(true);
        factory.addParameter(new SqlParameter(Types.INTEGER));
        factory.addParameter(new SqlParameter(Types.INTEGER));
        factory.addParameter(new SqlParameter(Types.VARCHAR));
        factory.addParameter(new SqlParameter(Types.BOOLEAN));

        return factory.newPreparedStatementCreator(Arrays.asList(
                review.getBook().getId(),
                review.getRating(),
                review.getDescription(),
                review.getRating(),
                review.isPublished()));
    }

    private Integer retrieveId(KeyHolder holder) throws SQLException {

        if (holder.getKeys() != null && holder.getKeys().size() > 0) {
            return (Integer) holder.getKeys().get("review_id");
        } else {
            return holder.getKey().intValue();
        }
    }
}
