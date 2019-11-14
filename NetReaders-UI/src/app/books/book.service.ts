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

  getByAuthor(arg: string): Observable<ResponseMessage<BookDto[]>> {
    return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/books/byauthor?id=${arg}&amount=5&offset=0`);
  }
  getByGenre(arg: string): Observable<ResponseMessage<BookDto[]>> {
    return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/books/bygenre?id=${arg}&amount=5&offset=0`);
  }
  getByName(name: string): Observable<ResponseMessage<BookDto[]>> {
    return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/books/byname?name=${name}&amount=5&offset=0`);
  }
}
