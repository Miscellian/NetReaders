import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

import {Book, UserBookLibrary} from '../model';

@Injectable({
    providedIn: 'root'
})
export class BookService {

    constructor(private httpClient: HttpClient) {
    }

    getById(id: number): Observable<Book> {
        return this.httpClient.get<Book>(`/books/${id}`);
    }

    getByAuthor(arg: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 8;
        return this.httpClient.get<Book[]>(`/books/byauthor?id=${arg}&amount=8&offset=${offset}`);
    }

    getByGenre(arg: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 8;
        return this.httpClient.get<Book[]>(`/books/bygenre?id=${arg}&amount=8&offset=${offset}`);
    }

    getByName(name: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 8;
        return this.httpClient.get<Book[]>(`/books/byname?name=${name}&amount=8&offset=${offset}`);
    }

    getByRange(page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 8;
        return this.httpClient.get<Book[]>(`/books/range?amount=8&offset=${offset}`);
    }

    getByUser(username: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 8;
        return this.httpClient.get<Book[]>(`/books/byusername?username=${username}&amount=8&offset=${offset}`);
    }

    getFavouritesByUser(username: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 8;
        return this.httpClient.get<Book[]>(`/books/byusernameFavourites?username=${username}&amount=8&offset=${offset}`);
    }

    getToReadListByUser(username: string, page: string): Observable<Book[]> {
        const offset = (Number(page) - 1) * 8;
        return this.httpClient.get<Book[]>(`/books/byusernameToReadList?username=${username}&amount=8&offset=${offset}`);
    }

    getByUserPreferences(username: string): Observable<Book[]> {
        return this.httpClient.get<Book[]>(`/books/preferences?username=${username}`);
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

    addToLibrary(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/addToLibrary`, userBook, {observe: 'response'});
    }

    checkInLibrary(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/checkInLibrary`, userBook);
    }

    removeFromLibrary(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/removeFromLibrary`, userBook, {observe: 'response'});
    }

    addToFavourites(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/addToFavourites`, userBook, {observe: 'response'});
    }

    checkInFavourites(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/checkInFavourites`, userBook);
    }

    removeFromFavourites(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/removeFromFavourites`, userBook, {observe: 'response'});
    }

    getFavouritesCountByUser(username: string): Observable<number> {
        return this.httpClient.get<number>(`/books/countFavouritesByUsername?username=${username}`);
    }

    addToToReadList(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/addToToReadList`, userBook, {observe: 'response'});
    }

    checkInToReadList(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/checkInToReadList`, userBook);
    }

    removeFromToReadList(userBook: UserBookLibrary) {
        return this.httpClient.post(`/books/removeFromToReadList`, userBook, {observe: 'response'});
    }

    getToReadListCountByUser(username: string): Observable<number> {
        return this.httpClient.get<number>(`/books/countToReadListByUsername?username=${username}`);
    }
}
