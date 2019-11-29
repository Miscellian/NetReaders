package com.netreaders.dao.review;

import com.netreaders.models.Review;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReviewMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {

        Review review = new Review();

        review.setId(rs.getInt("review_id"));
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
