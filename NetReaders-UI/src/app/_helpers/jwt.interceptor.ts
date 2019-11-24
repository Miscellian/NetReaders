import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthenticationService} from '../login/authentication.service';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) {}

    private type: String;
    private token: String;

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        let currentUser = this.authenticationService.currentUserValue;
        this.token=currentUser.token;
        this.type=currentUser.type;
        if (this.type && this.token) {
            request = request.clone({
                setHeaders: {
                    Authorization: `${this.type} ${this.token}`
                }
            });
        }

        return next.handle(request);
    }
}