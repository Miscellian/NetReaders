import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class ApiInterceptor implements HttpInterceptor  {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const url = 'http://netreaders.herokuapp.com';
    // const url = 'http://localhost:8080/api';
    req = req.clone({
      url: url + req.url
    });
    return next.handle(req);
  }
}
