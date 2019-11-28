package com.netreaders.dao.review;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.netreaders.models.Review;

import lombok.extern.log4j.Log4j;

@Log4j
@PropertySource("classpath:query.properties")
@Repository
public class ReviewDaoImpl implements ReviewDao {
	
	@Autowired
    private JdbcTemplate template;

    @Autowired
    private Environment env;

    @Autowired
    private ReviewMapper reviewMapper;

	@Override
	public Review create(Review review) throws SQLException {
		final String sql_query = env.getProperty("review.create");

        KeyHolder holder = new GeneratedKeyHolder();

        // save object into DB and return auto generated PK via KeyHolder
        // or throws DuplicateKeyException if record exist in table
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, review.getBook().getBook().getId());
                ps.setInt(2, review.getRating());
                ps.setString(3, review.getDescription());
                ps.setBoolean(4, review.isPublished());
                return ps;
            }, holder);

            Integer newId;
            if (holder.getKeys().size() > 1) {
                newId = (Integer) holder.getKeys().get("genre_id");
            } else {
                newId = holder.getKey().intValue();
            }
            review.setReviewId(newId);
            log.debug(String.format("Create a new review with id '%d'", newId));

            return review;

        } catch (DuplicateKeyException e) {
            log.error(String.format("Review '%d' is already exist", review.getReviewId()));
            throw new SQLException("Internal sql exception");
        }
	}

	@Override
	public Review getById(Integer id) throws SQLException {
		String sql_query = env.getProperty("review.read");

        List<Review> reviews = template.query(sql_query, reviewMapper, id);
        if (reviews.isEmpty()) {
            log.debug(String.format("Dont find any review by id '%d'", id));
            return null;
        } else if (reviews.size() == 1) {
            log.debug(String.format("Find a review by id '%d'", id));
            return reviews.get(0);
        } else {
            log.error(String.format("Find more than one review by id '%d'", id));
            throw new SQLException("Internal sql exception");
        }
	}

	@Override
	public void update(Review review) throws SQLException {
		String sql_query = env.getProperty("review.update");
        long id = review.getReviewId();

        int recordCount = template.update(sql_query, new Object[] {
        		review.getRating(),
        		review.getDescription(),
        		review.isPublished(),
        		id
        });
        
        if (recordCount == 0) {
            log.debug(String.format("Dont update any review by id '%d'", id));
        } else if (recordCount == 1) {
            log.debug(String.format("Update review by id '%d'", id));
        } else {
            log.error(String.format("Update more than one review by id '%d'", id));
            throw new SQLException("Internal sql exception");
        }
		
	}

	@Override
	public void delete(Review persistentObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Review> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Review> getByBookId(int bookID, int amount, int offset) throws SQLException {
		String sql_query = env.getProperty("review.getByBookId");

        List<Review> reviews = template.query(sql_query, reviewMapper, new Object[] {bookID,amount,offset});
        if (reviews.isEmpty()) {
            log.debug(String.format("Dont find any review by bookID '%d'", bookID));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d review(s) by bookID '%d'", reviews.size(), bookID));
            return reviews;
        }
	}

	public Collection<Review> getPublishedByBookId(int bookId, int amount, int offset) throws SQLException {
		String sql_query = env.getProperty("review.getPublishedByBookId");

        List<Review> reviews = template.query(sql_query, reviewMapper, new Object[] {bookId,amount,offset});
        if (reviews.isEmpty()) {
            log.debug(String.format("Dont find any  published review by bookID '%d'", bookId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d published review(s) by bookID '%d'", reviews.size(), bookId));
            return reviews;
        }
	}

	public Collection<Review> getUnpublishedByBookId(int bookId, int amount, int offset) throws SQLException {
		String sql_query = env.getProperty("review.getUnpublishedByBookId");

        List<Review> reviews = template.query(sql_query, reviewMapper, new Object[] {bookId,amount,offset});
        if (reviews.isEmpty()) {
            log.debug(String.format("Dont find any unpublished review by bookID '%d'", bookId));
            return Collections.emptyList();
        } else {
            log.debug(String.format("Find %d unpublished review(s) by bookID '%d'", reviews.size(), bookId));
            return reviews;
        }
	}

}
