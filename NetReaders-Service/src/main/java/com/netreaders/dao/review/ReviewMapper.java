package com.netreaders.dao.review;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.netreaders.dao.genres.GenreMapper;
import com.netreaders.models.Review;

@Component
public class ReviewMapper implements RowMapper<Review> {
	@Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {

		Review review = new Review();

		review.setReviewId(rs.getInt("review_id"));
		review.setRating(rs.getInt("rating"));
		review.setDescription(rs.getString("description"));
		review.setPublished(rs.getBoolean("published"));

        return review;
    }

    @Bean
    public ReviewMapper getReviewMapper() {
        return new ReviewMapper();
    }
}
