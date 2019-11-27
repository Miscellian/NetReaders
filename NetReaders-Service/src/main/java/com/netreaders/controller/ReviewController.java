package com.netreaders.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netreaders.models.Review;
import com.netreaders.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping("{id}")
	public Review getReviewById(@PathVariable int id) {
		return reviewService.getById(id);
	}

	@GetMapping("published/bybook")
	public Collection<Review> getPublishedReviewsByBook(@RequestParam(name = "id") int bookid,
			@RequestParam(name = "amount") int amount, @RequestParam(name = "offset") int offset) {
		return reviewService.getPublishedByBook(bookid, amount, offset);
	}
	
	@GetMapping("unpublished/bybook")
	public Collection<Review> getUnpublishedReviewsByBook(@RequestParam(name = "id") int bookid,
			@RequestParam(name = "amount") int amount, @RequestParam(name = "offset") int offset) {
		return reviewService.getUnpublishedByBookId(bookid, amount, offset);
	}
}
