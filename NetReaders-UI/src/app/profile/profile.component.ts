import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "./user.service";
import {User} from "../model";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    user: User;
    arg: string;

    constructor(private activatedRoute: ActivatedRoute,
                private  userService: UserService,
                public router: Router) {
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params['username'];
                this.userService.getByUsername(this.arg).subscribe(
                    response => this.user = response,
                    error => this.router.navigate(['/error'])
                );
            }
        );
    }
}
