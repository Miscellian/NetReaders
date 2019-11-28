package com.netreaders.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.netreaders.models.Review;
import com.netreaders.security.JwtProvider;
import com.netreaders.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getReviewById(@PathVariable int id,
								@RequestHeader(value = "Authorization",required = false) String token) {
		Review review = reviewService.getById(id);
		if(!review.isPublished()) {
			List<String> roles = new JwtProvider().getAuthoritiesFromToken(token);
			if(roles == null || !roles.contains("REVIEW_MODERATOR")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		return ResponseEntity.ok(review);
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
	
	@PostMapping("add")
	public ResponseEntity<?> createReview(@RequestBody Review review){
		review.setPublished(false);
		reviewService.addReview(review);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("publish")
	public ResponseEntity<?> publishReview(@RequestParam(name = "id") String reviewId) {
		reviewService.publishReview(Integer.parseInt(reviewId));
		return ResponseEntity.ok().build();
	}
	
}
