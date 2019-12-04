package com.netreaders.dao.review;

import com.netreaders.dao.GenericDao;
import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.models.Review;

import java.util.Collection;

public interface ReviewDao extends GenericDao<Review, Integer> {

    Collection<Review> getByBookId(Integer bookId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Review> getPublishedByBookId(Integer bookId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Review> getUnpublishedByBookId(Integer bookId, Integer amount, Integer offset) throws DataBaseSQLException;

    Collection<Review> getUnpublished(Integer amount, Integer offset) throws DataBaseSQLException;
    
    Integer getReviewsPublishedByBookCount(Integer bookId) throws DataBaseSQLException;

	Integer getReviewsUnpublishedByBookCount(Integer bookId) throws DataBaseSQLException;

	Integer getUnpublishedReviewsCount() throws DataBaseSQLException;
}
