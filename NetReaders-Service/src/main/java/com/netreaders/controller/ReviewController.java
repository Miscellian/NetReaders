package com.netreaders.controller;

import com.netreaders.models.Review;
import com.netreaders.security.JwtProvider;
import com.netreaders.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Integer id,
                                           @RequestHeader(value = "Authorization", required = false) String token) {
        Review review = reviewService.getReviewById(id);
        if (!review.isPublished()) {
            List<String> roles = new JwtProvider().getAuthoritiesFromToken(token);
            if (roles == null || !roles.contains("REVIEW_MODERATOR")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.ok(review);
    }

    @GetMapping("published/bybook")
    public Collection<Review> getPublishedReviewsByBook(@RequestParam(name = "id") Integer bookId,
                                                        @RequestParam(name = "amount") Integer amount, @RequestParam(name = "offset") Integer offset) {

        return reviewService.getReviewsPublishedByBook(bookId, amount, offset);
    }

    @GetMapping("unpublished/bybook")
    public Collection<Review> getUnpublishedReviewsByBook(@RequestParam(name = "id") Integer bookId,
                                                          @RequestParam(name = "amount") Integer amount, @RequestParam(name = "offset") Integer offset) {

        return reviewService.getReviewsUnpublishedByBookId(bookId, amount, offset);
    }

    @PostMapping("add")
    public ResponseEntity<?> createReview(@RequestBody Review review) {

        review.setPublished(false);
        reviewService.addReview(review);
        return ResponseEntity.ok().build();
    }

    @GetMapping("publish")
    public ResponseEntity<?> publishReview(@RequestParam(name = "id") Integer reviewId) {

        reviewService.publishReview(reviewId);
        return ResponseEntity.ok().build();
    }

}
