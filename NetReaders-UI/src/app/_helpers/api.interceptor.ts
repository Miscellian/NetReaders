import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class ApiInterceptor implements HttpInterceptor  {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // const url = 'https://netreaders.herokuapp.com/api';
    const url = 'http://localhost:8080/api';
    req = req.clone({
      url: url + req.url
    });
    return next.handle(req);
  }
}
