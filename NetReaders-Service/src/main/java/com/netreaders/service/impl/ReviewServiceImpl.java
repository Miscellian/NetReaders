package com.netreaders.service.impl;

import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.review.ReviewDao;
import com.netreaders.models.Review;
import com.netreaders.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    private final BookDao bookDao;

    @Override
    public Review getReviewById(Integer id) {

        Review review = reviewDao.getById(id);
        return modelToDto(review);
    }

    @Override
    public Collection<Review> getReviewsByBook(Integer bookId, Integer amount, Integer offset) {

        Collection<Review> reviews = reviewDao.getByBookId(bookId, amount, offset);
        return createDtoCollection(reviews);
    }

    @Override
    public Collection<Review> getReviewsPublishedByBook(Integer bookId, Integer amount, Integer offset) {

        Collection<Review> reviews = reviewDao.getPublishedByBookId(bookId, amount, offset);
        return createDtoCollection(reviews);
    }

    @Override
    public Collection<Review> getReviewsUnpublishedByBookId(Integer bookId, Integer amount, Integer offset) {

        Collection<Review> reviews = reviewDao.getUnpublishedByBookId(bookId, amount, offset);
        return createDtoCollection(reviews);

    }

    @Override
    public void addReview(Review review) {

        reviewDao.create(review);
    }

    @Override
    public void updateReview(Review review) {

        reviewDao.update(review);
    }

    @Override
    public void publishReview(Integer reviewId) {

        Review review = reviewDao.getById(reviewId);
        review.setPublished(true);
        updateReview(modelToDto(review));
    }

    private Collection<Review> createDtoCollection(Collection<Review> reviews) {

        return reviews.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private Review modelToDto(Review review) {

        review.setBook(bookDao.getByReviewId(review.getId()));
        return review;
    }
}
