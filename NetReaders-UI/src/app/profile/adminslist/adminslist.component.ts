import {Component, OnInit} from '@angular/core';
import {User} from "../../model";
import {UserService} from "../user.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-adminslist',
    templateUrl: './adminslist.component.html',
    styleUrls: ['./adminslist.component.css']
})
export class AdminslistComponent implements OnInit {
    users: User[];

    constructor(private userService: UserService,
                private router: Router) {
    }

    ngOnInit() {
        this.userService.getAdminsList().subscribe(
            response => this.users = response,
            error => this.router.navigate(['error'])
        );
    }

}