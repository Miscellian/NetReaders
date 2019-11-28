import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Review, BookDto } from '../model';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private httpClient: HttpClient) { }

  getById(id: number) {
    return this.httpClient.get(`http://localhost:8080/api/reviews/${id}`, {observe: 'response'});
  }
  getByBookId(arg: string, page: string): Observable<Review[]> {
    const offset = (Number(page) - 1) * 5;
    return this.httpClient.get<Review[]>(`http://localhost:8080/api/reviews/byauthor?id=${arg}&amount=5&offset=${offset}`);
  }
  getPublishedByBookId(arg: string, page: string): Observable<Review[]> {
    const offset = (Number(page) - 1) * 5;
    return this.httpClient.get<Review[]>(`http://localhost:8080/api/reviews/published/bybook?id=${arg}&amount=5&offset=${offset}`);
  }
  getUnpublishedByBookId(arg: string, page: string): Observable<Review[]> {
    const offset = (Number(page) - 1) * 5;
    return this.httpClient.get<Review[]>(`http://localhost:8080/api/reviews/unpublished/bybook?id=${arg}&amount=5&offset=${offset}`);
  }
  createReview(reviewForm: FormGroup, book: BookDto) {
    let review: Review = new Review();
    review.rating = reviewForm.value['rating'];
    review.description = reviewForm.value['description'];
    review.published = false;
    review.book = book;
    return this.httpClient.post(`http://localhost:8080/api/reviews/add`, review, {observe: 'response'});
  }
  publishReview(review: Review) {
    review.published = true;
    return this.httpClient.get(`http://localhost:8080/api/reviews/publish?id=${review.reviewId}`, {observe: 'response'});
  }
}
