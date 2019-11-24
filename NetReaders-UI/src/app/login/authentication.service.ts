import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {LoginInfo, ResponseMessage, User} from "../model";
import {JwtInterceptor} from "../_helpers/jwt.interceptor";

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<LoginInfo>;
    public currentUser: Observable<LoginInfo>;

    constructor(private http: HttpClient, private jwtInterceptor: JwtInterceptor) {
        this.currentUserSubject = new BehaviorSubject<LoginInfo>(JSON.parse(localStorage.getItem('currentToken')));
        this.currentUser = this.currentUserSubject.asObservable();
    }


    public get currentUserValue(): LoginInfo {
        return this.currentUserSubject.value;
    }

    login(username, password): Observable<LoginInfo>{
        return this.http.post<LoginInfo>(`http://localhost:8080/api/login`, { username, password })
            .pipe(map(user => {
            //    // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('currentToken', user.token);
             //   this.currentUserSubject.next(user);
                return user;
            }));
    }

    logout() {
        // remove user from local storage and set current user to null
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}