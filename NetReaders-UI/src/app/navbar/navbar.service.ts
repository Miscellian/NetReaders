import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Genre } from '../model';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {

  constructor(private httpClient: HttpClient) { }

  getAllGenres(): Observable<Genre[]> {
    return this.httpClient.get<Genre[]>(`/genres/all`);
  }
}
