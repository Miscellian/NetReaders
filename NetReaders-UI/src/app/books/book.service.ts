import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

import {BookDto, ResponseMessage} from '../model';

@Injectable({
    providedIn: 'root'
})
export class BookService {

    constructor(private httpClient: HttpClient) {
    }

    getById(id: number): Observable<ResponseMessage<BookDto>> {
        return this.httpClient.get<ResponseMessage<BookDto>>(`http://localhost:8080/api/books/${id}`);
    }

    getByAuthor(arg: string, page: string): Observable<ResponseMessage<BookDto[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/books/byauthor?id=${arg}&amount=5&offset=${offset}`);
    }

    getByGenre(arg: string, page: string): Observable<ResponseMessage<BookDto[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/books/bygenre?id=${arg}&amount=5&offset=${offset}`);
    }

    getByName(name: string, page: string): Observable<ResponseMessage<BookDto[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/books/byname?name=${name}&amount=5&offset=${offset}`);
    }

    getByRange(page: string): Observable<ResponseMessage<BookDto[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/books/range?amount=5&offset=${offset}`);
    }

    getByUser(username: string, page: string): Observable<ResponseMessage<BookDto[]>> {
        var offset = (Number(page) - 1) * 5;
        return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/books/byusername?username=${username}&amount=5&offset=${offset}`);
    }

    getCount(): Observable<ResponseMessage<number>> {
        return this.httpClient.get<ResponseMessage<number>>(`http://localhost:8080/api/books/count`);
    }

    getCountByAuthor(authorId: string): Observable<ResponseMessage<number>> {
        return this.httpClient.get<ResponseMessage<number>>(`http://localhost:8080/api/books/countByAuthor?id=${authorId}`);
    }

    getCountByGenre(genreId: string): Observable<ResponseMessage<number>> {
        return this.httpClient.get<ResponseMessage<number>>(`http://localhost:8080/api/books/countByGenre?id=${genreId}`);
    }

    getCountByName(name: string): Observable<ResponseMessage<number>> {
        return this.httpClient.get<ResponseMessage<number>>(`http://localhost:8080/api/books/countByName?name=${name}`);
    }

    getCountByUser(username: string): Observable<ResponseMessage<number>> {
        return this.httpClient.get<ResponseMessage<number>>(`http://localhost:8080/api/books/countByUsername?username=${username}`);
    }
}
