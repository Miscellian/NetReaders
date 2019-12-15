import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {User} from '../../model';
import {UserService} from '../user.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-edit-moderator',
    templateUrl: './edit-moderator.component.html',
    styleUrls: ['./edit-moderator.component.css']
})
export class EditModeratorComponent implements OnInit {
    editModeratorForm: FormGroup;
    moderator: User;
    localUsername: string;
    moderatorUsername: string;
    selectedRoles: string[] = [];
    moderatorRoles: any = [
        {
            name: 'Announcement',
            value: 'ANNOUNCEMENT_MODERATOR',
            selected: false
        },
        {
            name: 'Review',
            value: 'REVIEW_MODERATOR',
            selected: false
        },
        {
            name: 'Overview',
            value: 'OVERVIEW_MODERATOR',
            selected: false
        }
    ];

    constructor(private userService: UserService,
                private router: Router,
                private activatedRoute: ActivatedRoute,
                private formBuilder: FormBuilder) {
        if (!this.userService.hasAuthority('ADMIN') &&
            !this.userService.hasAuthority('SUPER_ADMIN')) {
            this.router.navigate(['/home']);
        }
        this.localUsername = localStorage.getItem('UserName');
    }

    get getFormValue() {
        return this.editModeratorForm.controls;
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.moderatorUsername = params.username;
                this.userService.getByUsername(this.moderatorUsername).subscribe(
                    response => this.moderator = response,
                    () => this.router.navigate(['/error'])
                );
            }
        );
        this.editModeratorForm = this.formBuilder.group({
            roles: this.createRoles(this.moderatorRoles)
        });
    }

    createRoles(rolesInput) {
        const arr = rolesInput.map(role => {
            return new FormControl(role.selected || false);
        });
        return new FormArray(arr);
    }

    onSubmit() {
        if (this.getFormValue.roles.value[0] === false &&
            this.getFormValue.roles.value[1] === false &&
            this.getFormValue.roles.value[2] === false) {
            alert('Select at least one role for moderator');
            return;
        }
        this.getFormValue.roles.value.forEach((value, i) => {
            if (value === true) {
                this.selectedRoles.push(this.moderatorRoles[i].value);
            }
        });
        this.userService.updateModeratorRoles(this.selectedRoles, this.moderator.username).subscribe(
            () => this.router.navigate(['/users', localStorage.getItem('UserName')]),
            () => this.router.navigate(['error'])
        );
    }
}
