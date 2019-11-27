import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "./user.service";
import {User} from "../../model";

@Component({
    selector: 'app-userpage',
    templateUrl: './userpage.component.html',
    styleUrls: ['./userpage.component.css']
})
export class UserpageComponent implements OnInit {

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
                this.userService.getByUsername(this.arg).subscribe(response => {
                    if (!response.isSuccessful) {
                        this.router.navigate(['/error']);
                    } else {
                        this.user = response.obj;
                    }
                })
            }
        );
    }

}
