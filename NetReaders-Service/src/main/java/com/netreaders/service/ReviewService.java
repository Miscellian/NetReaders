package com.netreaders.service;

import com.netreaders.models.Review;

import java.util.Collection;

public interface ReviewService {

    Review getReviewById(Integer id);

    Collection<Review> getReviewsByBook(Integer bookId, Integer amount, Integer offset);

    Collection<Review> getReviewsPublishedByBook(Integer bookId, Integer amount, Integer offset);

    Collection<Review> getReviewsUnpublishedByBookId(Integer bookId, Integer amount, Integer offset);
    
    Collection<Review> getUnpublishedReviews(Integer amount, Integer offset);

    void addReview(Review review);

    void updateReview(Review review);

    void publishReview(Integer reviewId);

	Integer getReviewsPublishedByBookCount(Integer bookId);

	Integer getReviewsUnpublishedByBookCount(Integer bookId);

	Integer getUnpublishedReviewsCount();


}
