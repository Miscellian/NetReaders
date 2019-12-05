import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

import {Announcement} from '../model';

@Injectable({
    providedIn: 'root'
})
export class AnnouncementService {

    constructor(private httpClient: HttpClient) {
    }

    getById(id: number): Observable<Announcement> {
        return this.httpClient.get<Announcement>(`/announcements/${id}`);
    }

    getByAuthor(id: number, year: number, month: number): Observable<Announcement[]> {
        return this.httpClient.get<Announcement[]>(`/announcements/byauthor?id=${id}&year=${year}&month=${month}`);
    }

    getByGenre(id: number, year: number, month: number): Observable<Announcement[]> {
        return this.httpClient.get<Announcement[]>(`/announcements/bygenre?id=${id}&year=${year}&month=${month}`);
    }

    getAll(year: number, month: number): Observable<Announcement[]> {
        return this.httpClient.get<Announcement[]>(`/announcements/all?year=${year}&month=${month}`);
    }
}
