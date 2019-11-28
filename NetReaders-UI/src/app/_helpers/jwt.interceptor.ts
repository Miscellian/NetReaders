import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthenticationService} from '../login/authentication.service';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) {}

    private type: string;
    private token: string;

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // const currentUser = this.authenticationService.currentUserValue;
        this.token = localStorage.getItem('TokenValue');
        this.type = localStorage.getItem('TokenType');
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
