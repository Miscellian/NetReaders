import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

import {Book, Author, BookDto, ResponseMessage} from '../model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient) { }

  getById(id: number): Observable<ResponseMessage<BookDto>> {
    return this.httpClient.get<ResponseMessage<BookDto>>(`http://localhost:8080/api/books/${id}`);
  }

  getByAuthor(id: number): Observable<ResponseMessage<BookDto>> {
    const params = new HttpParams().set('id', id.toString());
    return this.httpClient.get<ResponseMessage<BookDto>>(`localhost:8080/api/books/byathor`, {params});
  }
}
