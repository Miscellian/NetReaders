import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {LoginInfo, ResponseMessage, User} from '../model';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<LoginInfo>;
    public currentUser: Observable<LoginInfo>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<LoginInfo>(JSON.parse(localStorage.getItem('currentToken')));
        this.currentUser = this.currentUserSubject.asObservable();
    }


    // public get currentUserValue(): LoginInfo {
    //     return this.currentUserSubject.value;
    // }

    login(username, password): Observable<LoginInfo> {
        return this.http.post<LoginInfo>(`/users/login`, { username, password })
            .pipe(map(user => {
                localStorage.setItem('TokenValue', user.token);
                localStorage.setItem('TokenType', user.type);
                localStorage.setItem('UserName', user.username);
                localStorage.setItem('Authorities', JSON.stringify(user.authorities));
                this.currentUserSubject.next(user);
                return user;
            }));
    }

    logout() {
        // remove user from local storage and set current user to null
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}
