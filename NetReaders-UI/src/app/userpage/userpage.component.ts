import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "./user.service";
import {User} from "../model";

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
        this.arg = this.activatedRoute.snapshot.data['id'];
        this.userService.getById(this.arg).subscribe(
            response => {
              if (response.isSuccessful) {
                this.user = response.obj;
              } else {
                this.router.navigate(['/error']);
              }
            }
        )
    }

}
