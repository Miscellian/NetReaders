import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

import {Book, ResponseMessage} from '../model';

@Injectable({
    providedIn: 'root'
})
export class BookService {

    constructor(private httpClient: HttpClient) {
    }

    getById(id: number): Observable<Book> {
        return this.httpClient.get<Book>(`/books/${id}`) ;
    }

    getByAuthor(arg: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`/books/byauthor?id=${arg}&amount=5&offset=${offset}`);
    }

    getByGenre(arg: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`/books/bygenre?id=${arg}&amount=5&offset=${offset}`);
    }

    getByName(name: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`/books/byname?name=${name}&amount=5&offset=${offset}`);
    }

    getByRange(page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`/books/range?amount=5&offset=${offset}`);
    }

    getByUser(username: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 5;
        return this.httpClient.get<Book[]>(`/books/byusername?username=${username}&amount=5&offset=${offset}`);
    }

    getCount(): Observable<number> {
        return this.httpClient.get<number>(`/books/count`);
    }

    getCountByAuthor(authorId: string): Observable<number> {
        return this.httpClient.get<number>(`/books/countByAuthor?id=${authorId}`);
    }

    getCountByGenre(genreId: string): Observable<number> {
        return this.httpClient.get<number>(`/books/countByGenre?id=${genreId}`);
    }

    getCountByName(name: string): Observable<number> {
        return this.httpClient.get<number>(`/books/countByName?name=${name}`);
    }

    getCountByUser(username: string): Observable<number> {
        return this.httpClient.get<number>(`/books/countByUsername?username=${username}`);
    }
}
