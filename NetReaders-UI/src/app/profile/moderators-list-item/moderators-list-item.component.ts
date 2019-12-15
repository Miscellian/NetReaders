import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model";
import {UserService} from "../user.service";
import {Router} from "@angular/router";
import {ModeratorslistComponent} from "../moderatorslist/moderatorslist.component";

@Component({
  selector: 'app-moderators-list-item',
  templateUrl: './moderators-list-item.component.html',
  styleUrls: ['./moderators-list-item.component.css']
})
export class ModeratorsListItemComponent implements OnInit {
  @Input() moderator: User;

  constructor(private moderatorsList: ModeratorslistComponent,
              private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
  }

  removeModerator(moderator: User) {
    this.userService.removeModerator(moderator).subscribe(
        response => this.moderatorsList.getModeratorsList(),
        error => this.router.navigate(['/error'])
    );
  }
}
