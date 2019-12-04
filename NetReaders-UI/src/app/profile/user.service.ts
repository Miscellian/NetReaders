import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  getByUsername(username: string): Observable<User> {
    return this.httpClient.get<User>(`/users/${username}`);
  }

  getAdminsList(): Observable<User[]> {
    return this.httpClient.get<User[]>(`/users/getAdminsList`);
  }

  getModeratorsList(): Observable<User[]> {
    return this.httpClient.get<User[]>(`/users/getModeratorsList`);
  }
}
