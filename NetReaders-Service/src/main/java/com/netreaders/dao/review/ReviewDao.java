package com.netreaders.dao.review;

import java.sql.SQLException;
import java.util.Collection;

import com.netreaders.dao.GenericDao;
import com.netreaders.models.Review;

public interface ReviewDao extends GenericDao<Review, Integer> {
	Collection<Review> getPublishedByBookId(int bookId,int amount, int offset) throws SQLException;
	Collection<Review> getUnpublishedByBookId(int bookId,int amount, int offset) throws SQLException;
	Collection<Review> getByBookId(int bookID, int amount, int offset) throws SQLException;
}
