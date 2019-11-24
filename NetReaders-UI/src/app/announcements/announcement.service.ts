import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Announcement, ResponseMessage} from "../model";

@Injectable({
    providedIn: 'root'
})
export class AnnouncementService {

    constructor(private httpClient: HttpClient) {
    }

    getAll(): Observable<ResponseMessage<Announcement>> {
        return this.httpClient.get<ResponseMessage<Announcement>>(`http://localhost:8080/api/announcements/all`)
    }

    getById(id: number): Observable<ResponseMessage<Announcement>> {
        return this.httpClient.get<ResponseMessage<Announcement>>(`http://localhost:8080/api/announcements/${id}`)
    }

    getByAuthor(arg: string): Observable<ResponseMessage<Announcement[]>> {
        return this.httpClient.get<ResponseMessage<Announcement[]>>(`http://localhost:8080/api/announcements/byauthor?id=${arg}&amount=5&offset=0`);
    }

    getByGenre(arg: string): Observable<ResponseMessage<Announcement[]>> {
        return this.httpClient.get<ResponseMessage<Announcement[]>>(`http://localhost:8080/api/announcements/bygenre?id=${arg}&amount=5&offset=0`);
    }
}
