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
    admins: User[];

    constructor(private userService: UserService,
                private router: Router) {
    }

    ngOnInit() {
        this.getAdminsList();
    }

    getAdminsList() {
        this.userService.getAdminsList().subscribe(
            response => this.admins = response,
            error => this.router.navigate(['error'])
        );
    }

    removeAdmin(admin: User) {
        this.userService.removeAdmin(admin).subscribe(
            response => this.getAdminsList(),
            error => this.router.navigate(['/error'])
        );
    }
}
