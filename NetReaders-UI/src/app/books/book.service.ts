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
    return this.httpClient.get<ResponseMessage<BookDto>>(`//netreaders.herokuapp.com/api/books/${id}`);
  }
  getByAuthor(arg: string, page: string): Observable<ResponseMessage<BookDto[]>> {
    var offset = (Number(page) - 1) * 5;
    return this.httpClient.get<ResponseMessage<BookDto[]>>(`//netreaders.herokuapp.com/api/books/byauthor?id=${arg}&amount=5&offset=${offset}`);
  }
  getByGenre(arg: string, page: string): Observable<ResponseMessage<BookDto[]>> {
    var offset = (Number(page) - 1) * 5;
    return this.httpClient.get<ResponseMessage<BookDto[]>>(`//netreaders.herokuapp.com/api/books/bygenre?id=${arg}&amount=5&offset=${offset}`);
  }
  getByName(name: string, page: string): Observable<ResponseMessage<BookDto[]>> {
    var offset = (Number(page) - 1) * 5;
    return this.httpClient.get<ResponseMessage<BookDto[]>>(`//netreaders.herokuapp.com/api/books/byname?name=${name}&amount=5&offset=${offset}`);
  }
  getByRange(page: string): Observable<ResponseMessage<BookDto[]>> {
    var offset = (Number(page) - 1) * 5;
    return this.httpClient.get<ResponseMessage<BookDto[]>>(`//netreaders.herokuapp.com/api/books/range?amount=5&offset=${offset}`);
  }
  getCount(): Observable<ResponseMessage<number>> {
    return this.httpClient.get<ResponseMessage<number>>(`//netreaders.herokuapp.com/api/books/count`);
  }
  getCountByAuthor(arg: string): Observable<ResponseMessage<number>> {
    return this.httpClient.get<ResponseMessage<number>>(`//netreaders.herokuapp.com/api/books/countByAuthor?id=${arg}`);
  }
  getCountByGenre(arg: string): Observable<ResponseMessage<number>> {
    return this.httpClient.get<ResponseMessage<number>>(`//netreaders.herokuapp.com/api/books/countByGenre?id=${arg}`);
  }
  getCountByName(arg: string): Observable<ResponseMessage<number>> {
    return this.httpClient.get<ResponseMessage<number>>(`//netreaders.herokuapp.com/api/books/countByName?name=${arg}`);
  }
}
