package com.netreaders.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.netreaders.dao.book.BookDao;
import com.netreaders.dao.review.ReviewDao;
import com.netreaders.models.Book;
import com.netreaders.models.Review;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewService {
	private ReviewDao reviewDao;
	private BookDao bookDao;
	
	private Review addBooktoReview(Review review) {
		try {
			review.setBook(bookDao.getByReviewId(review.getReviewId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return review;
	}
	public void addReview(Review review) throws SQLException{
		reviewDao.create(review);
	}
	
	public Collection<Review> getByBook(int bookId, int amount, int offset){
		Collection<Review> reviews = null;
		try {
			reviews = reviewDao.getByBookId(bookId, amount, offset)
					 .stream()
					 .map(this::addBooktoReview)
					 .collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	public Collection<Review> getPublishedByBook(int bookId, int amount, int offset){
		Collection<Review> reviews = null;
		try {
			reviews = reviewDao.getPublishedByBookId(bookId, amount, offset)
					 .stream()
					 .map(this::addBooktoReview)
					 .collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	public Collection<Review> getUnpublishedByBookId(int bookId, int amount, int offset){
		Collection<Review> reviews= null;
		try {
			reviews = reviewDao.getUnpublishedByBookId(bookId, amount, offset)
					 .stream()
					 .map(this::addBooktoReview)
					 .collect(Collectors.toList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
		
	}
	
	public void updateReview(Review review){
		try {
			reviewDao.update(review);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Review getById(int id){
		try {
			return reviewDao.getById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
