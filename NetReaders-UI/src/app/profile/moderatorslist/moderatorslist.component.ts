import {Component, OnInit} from '@angular/core';
import {User} from "../../model";
import {UserService} from "../user.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-moderatorslist',
    templateUrl: './moderatorslist.component.html',
    styleUrls: ['./moderatorslist.component.css']
})
export class ModeratorslistComponent implements OnInit {
    moderators: User[];

    constructor(private userService: UserService,
                private router: Router) {
    }

    ngOnInit() {
        this.getModeratorsList();
    }

    getModeratorsList() {
        this.userService.getModeratorsList().subscribe(
            response => this.moderators = response,
            error => this.router.navigate(['error'])
        );
    }
}
