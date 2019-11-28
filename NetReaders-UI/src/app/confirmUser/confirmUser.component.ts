import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ResponseMessage} from "../model";

@Component({
    selector: 'app-confirm',
    templateUrl: './confirmUser.component.html'
})
export class ConfirmUserComponent implements OnInit {

    token: string;
    private querySubscription: Subscription;
    constructor(private route: ActivatedRoute, private httpClient: HttpClient){
        this.querySubscription = route.queryParams.subscribe(
            (queryParam: any) => {
                this.token = queryParam['token'];
        });
    }

    confirm() {
        this.httpClient.get<ResponseMessage<String>>(`http://localhost:8080/api/users/confirmRegistration?token=${this.token}`);
    }
    ngOnInit(): void {
        this.confirm()
    }
}