import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import {Book, Author} from '../model';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient) { }

  getById(id: number): Observable<Book> {
    // return this.httpClient.get<Book>(`url/${id}`);
    return of(Object.assign(new Book(),
       {id: 1, name: 'TestBook', genre: 'Genre1',
        author: 'TestAuthor', announcementDate: Date.now(), description: 'asdqfqfadgasdhgoiagoifu'}));
  }

  getByAuthor(id: number): Observable<Author[]> {
    return null;
  }
}
