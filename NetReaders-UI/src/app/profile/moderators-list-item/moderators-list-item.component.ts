import {Component, Input, OnInit} from '@angular/core';
import {Role, User} from '../../model';
import {UserService} from '../user.service';
import {Router} from '@angular/router';
import {ModeratorslistComponent} from '../moderatorslist/moderatorslist.component';

@Component({
    selector: 'app-moderators-list-item',
    templateUrl: './moderators-list-item.component.html',
    styleUrls: ['./moderators-list-item.component.css']
})
export class ModeratorsListItemComponent implements OnInit {
    @Input() moderator: User;
    moderatorRoles: Role[];

    constructor(private moderatorsList: ModeratorslistComponent,
                private userService: UserService,
                private router: Router) {
    }

    ngOnInit() {
        this.userService.getRolesForModerator(this.moderator.username).subscribe(
            response => this.moderatorRoles = response,
            () => this.router.navigate(['/error'])
        );
    }

    removeModerator(moderator: User) {
        this.userService.removeModerator(moderator).subscribe(
            response => this.moderatorsList.getModeratorsList(),
            error => this.router.navigate(['/error'])
        );
    }

    splitRole(moderatorRole: string): string {
        return moderatorRole.split('_')[0].toLowerCase();
    }
}
