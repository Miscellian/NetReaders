import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BookDto, ResponseMessage} from "../../model";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserbooklistService {

    constructor(private httpClient: HttpClient) {
    }

    getBooksList(username: string): Observable<ResponseMessage<BookDto[]>> {
        return this.httpClient.get<ResponseMessage<BookDto[]>>(`http://localhost:8080/api/userbooks/${username}`);
    }
}
