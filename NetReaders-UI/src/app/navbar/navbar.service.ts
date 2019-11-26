import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResponseMessage, BookDto, Genre } from '../model';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {

  constructor(private httpClient: HttpClient) { }

  getAllGenres(): Observable<ResponseMessage<Genre[]>> {
    return this.httpClient.get<ResponseMessage<Genre[]>>(`http://localhost:8080/api/genres/all`);
  }
}
