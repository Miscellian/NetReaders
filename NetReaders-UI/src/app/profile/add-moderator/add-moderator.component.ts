import {Component, OnInit} from '@angular/core';
import {User} from '../../model';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
    selector: 'app-add-moderator',
    templateUrl: './add-moderator.component.html',
    styleUrls: ['./add-moderator.component.css']
})
export class AddModeratorComponent implements OnInit {
    user: User;
    addModeratorForm: FormGroup;
    submitted = false;
    loading = false;
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


    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private userService: UserService) {
        if (!this.userService.hasAuthority('ADMIN') &&
            !this.userService.hasAuthority('SUPER_ADMIN')) {
            this.router.navigate(['/home']);
        }
    }

    get getFormValue() {
        return this.addModeratorForm.controls;
    }

    ngOnInit() {
        this.userService.getByUsername(localStorage.getItem('UserName')).subscribe(
            response => this.user = response,
            error => this.router.navigate(['error'])
        );
        this.addModeratorForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.minLength(3)]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, this.userService.passwordValidator]],
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
        this.submitted = true;

        if (this.getFormValue.roles.value[0] === false &&
            this.getFormValue.roles.value[1] === false &&
            this.getFormValue.roles.value[2] === false) {
            alert('Select at least one role for moderator');
            return;
        }

        if (!this.addModeratorForm.touched) {
            alert('Fill all fields');
            return;
        }

        if (this.addModeratorForm.invalid) {
            return;
        }

        this.userService.checkIfUsernameExists(this.getFormValue.username.value).subscribe(
            response => {
                if (response === true) {
                    alert('This username already exists, please try another one');
                    return;
                } else {
                    this.userService.checkIfEmailExists(this.getFormValue.email.value).subscribe(
                        response => {
                            if (response === true) {
                                alert('This email already exists, please try another one');
                                return;
                            } else {
                                this.loading = true;
                                this.getFormValue.roles.value.forEach((value, i) => {
                                    if (value === true) {
                                        this.selectedRoles.push(this.moderatorRoles[i].value);
                                    }
                                });
                                this.userService.createModerator(this.addModeratorForm, this.selectedRoles)
                                    .subscribe(
                                        () => this.router.navigate(['/users', this.user.username]),
                                        error => this.router.navigate(['/error']));
                            }
                        },
                        error => this.router.navigate(['/error'])
                    );
                }
            },
            error => this.router.navigate(['/error']));
    }
}
