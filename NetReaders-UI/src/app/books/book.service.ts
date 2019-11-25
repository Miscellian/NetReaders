import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';

import {Book} from '../model';

@Injectable({
    providedIn: 'root'
})
export class BookService {

    constructor(private httpClient: HttpClient) {
    }

    getById(id: number): Observable<HttpResponse<Book>> {
        return this.httpClient.get<Book>(`http://localhost:8080/api/books/${id}`, { observe: 'response' });
    }

    getByAuthor(arg: string, page: string): Observable<HttpResponse<Book[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`http://localhost:8080/api/books/byauthor?id=${arg}&amount=5&offset=${offset}`, { observe: 'response' });
    }

    getByGenre(arg: string, page: string): Observable<HttpResponse<Book[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`http://localhost:8080/api/books/bygenre?id=${arg}&amount=5&offset=${offset}`, { observe: 'response' });
    }

    getByName(name: string, page: string): Observable<HttpResponse<Book[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`http://localhost:8080/api/books/byname?name=${name}&amount=5&offset=${offset}`, { observe: 'response' });
    }

    getByRange(page: string): Observable<HttpResponse<Book[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`http://localhost:8080/api/books/range?amount=5&offset=${offset}`, {observe: 'response'});
    }

    getCount(): Observable<HttpResponse<number>> {
        return this.httpClient.get<number>(`http://localhost:8080/api/books/count`, { observe: 'response' });
    }

    getCountByAuthor(arg: string): Observable<HttpResponse<number>> {
        return this.httpClient.get<number>(`http://localhost:8080/api/books/countByAuthor?id=${arg}`, { observe: 'response' });
    }

    getCountByGenre(arg: string): Observable<HttpResponse<number>> {
        return this.httpClient.get<number>(`http://localhost:8080/api/books/countByGenre?id=${arg}`, { observe: 'response' });
    }

    getCountByName(arg: string): Observable<HttpResponse<number>> {
        return this.httpClient.get<number>(`http://localhost:8080/api/books/countByName?name=${arg}`, { observe: 'response' });
    }
}
