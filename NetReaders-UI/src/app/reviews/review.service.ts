import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Review, Book } from '../model';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private httpClient: HttpClient) { }

  getById(id: number) {
    return this.httpClient.get(`/reviews/${id}`, {observe: 'response'});
  }
  getByBookId(arg: number, page: number): Observable<Review[]> {
    const offset = (page - 1) * 5;
    return this.httpClient.get<Review[]>(`/reviews/byauthor?id=${arg}&amount=5&offset=${offset}`);
  }
  getPublishedByBookId(arg: number, page: number): Observable<Review[]> {
    const offset = (page - 1) * 5;
    return this.httpClient.get<Review[]>(`/reviews/published/bybook?id=${arg}&amount=5&offset=${offset}`);
  }
  getUnpublishedByBookId(arg: number, page: number): Observable<Review[]> {
    const offset = (page - 1) * 5;
    return this.httpClient.get<Review[]>(`/reviews/unpublished/bybook?id=${arg}&amount=5&offset=${offset}`);
  }
  getUnpublished(page: number): Observable<Review[]> {
    const offset = (page - 1) * 5;
    return this.httpClient.get<Review[]>(`/reviews/unpublished?amount=5&offset=${offset}`);
  }
  getPublishedByBookIdCount(arg: number): Observable<number> {
    return this.httpClient.get<number>(`/reviews/published/countBybook?id=${arg}`);
  }
  getUnpublishedByBookIdCount(arg: number): Observable<number> {
    return this.httpClient.get<number>(`/reviews/unpublished/countBybook?id=${arg}`);
  }
  getUnpublishedCount(): Observable<number> {
    return this.httpClient.get<number>(`/reviews/unpublishedCount`);
  }

  createReview(reviewForm: FormGroup, book: Book) {
    const review: Review = { id: 0, rating: parseInt(reviewForm.value['rating']),
                          description: reviewForm.value['description'],
                          published: false, book: book};
    return this.httpClient.post(`/reviews/add`, review, {observe: 'response'});
  }
  publishReview(review: Review) {
    review.published = true;
    return this.httpClient.get(`/reviews/publish?id=${review.id}`, {observe: 'response'});
  }
}
