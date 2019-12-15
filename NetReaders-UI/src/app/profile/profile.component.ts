import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from './user.service';
import {User} from '../model';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    user: User;
    arg: string;
    storageUsername: string;
    authorities: string[];

    constructor(private activatedRoute: ActivatedRoute,
                private userService: UserService,
                public router: Router) {
        this.storageUsername = localStorage.getItem('UserName');
        if (this.storageUsername) {
            this.authorities = JSON.parse(localStorage.getItem('Authorities')).map(val => val.authority);
        } else {
            this.authorities = [];
        }
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params.username;
                this.userService.getByUsername(this.arg).subscribe(
                    response => {
                        this.user = response;
                    },
                    () => this.router.navigate(['/error'])
                );
            }
        );
    }
}
