import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Review } from '../model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private httpClient: HttpClient) { }

  getById(id: number){
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
}
