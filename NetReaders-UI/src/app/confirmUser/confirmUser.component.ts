import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../login/authentication.service';
import {Observable, Subscription} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ResponseMessage, User} from '../model';

@Component({
    selector: 'app-confirm',
    templateUrl: './confirmUser.component.html'
})
export class ConfirmUserComponent implements OnInit {

    token: string;
    private querySubscription: Subscription;
    constructor(private route: ActivatedRoute,
                private httpClient: HttpClient,
                private router: Router) {
        this.querySubscription = route.queryParams.subscribe(
            (queryParam: any) => {
                this.token = queryParam['token'];
        });
    }

    confirm() {
        console.dir('b');
        this.httpClient.get(`http://localhost:8080/api/users/confirmRegistration?token=${this.token}`, {observe: 'response'})
        .subscribe(response => {
            if (response.status !== 200) {
                this.router.navigate(['/error']);
            } else {
                this.router.navigate(['/login']);
            }
        });
    }
    ngOnInit(): void {
        this.confirm();
    }
}
