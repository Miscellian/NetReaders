import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Announcement} from "../model";

@Injectable({
    providedIn: 'root'
})
export class AnnouncementService {

    constructor(private httpClient: HttpClient) {
    }

    getAll(): Observable<HttpResponse<Announcement>> {
        return this.httpClient.get<Announcement>(`http://localhost:8080/api/announcements/all`, {observe: 'response'})
    }

    getById(id: number): Observable<HttpResponse<Announcement>> {
        return this.httpClient.get<Announcement>(`http://localhost:8080/api/announcements/${id}`, {observe: 'response'})
    }

    getByAuthor(arg: string): Observable<HttpResponse<Announcement[]>> {
        return this.httpClient.get<Announcement[]>(`http://localhost:8080/api/announcements/byauthor?id=${arg}&amount=5&offset=0`, {observe: 'response'});
    }

    getByGenre(arg: string): Observable<HttpResponse<Announcement[]>> {
        return this.httpClient.get<Announcement[]>(`http://localhost:8080/api/announcements/bygenre?id=${arg}&amount=5&offset=0`, {observe: 'response'});
    }
}
