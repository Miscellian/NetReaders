import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseMessage, User} from "../../model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  getByUsername(username: string): Observable<ResponseMessage<User>> {
    return this.httpClient.get<ResponseMessage<User>>(`http://localhost:8080/api/users/${username}`);
  }
}
