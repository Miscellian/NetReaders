import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import {User} from "../../model";
import {AuthenticationService} from "../../login/authentication.service";

@Component({
    selector: 'app-edit-profile',
    templateUrl: './edit-profile.component.html',
    styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
    user: User;
    editUserForm: FormGroup;
    submitted = false;
    loading = false;
    arg: string;

    constructor(private formBuilder: FormBuilder,
                private router: Router,
                private userService: UserService,
                private activatedRoute: ActivatedRoute,
                private authentificationService: AuthenticationService) {
    }

    get getFormValue() {
        return this.editUserForm.controls;
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe(
            params => {
                this.arg = params['username'];
                this.userService.getByUsername(this.arg).subscribe(
                    response => {
                        this.user = response;
                        this.setDefaultValues();
                    },
                    error => this.router.navigate(['/error'])
                );
            }
        );
        this.editUserForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.minLength(4)]],
            firstname: ['', [Validators.required, Validators.minLength(2)]],
            lastname: ['', [Validators.required, Validators.minLength(2)]],
            email: ['', [Validators.required, Validators.email]],
        });
    }

    onSubmit() {
        this.submitted = true;

        if (!this.editUserForm.touched ||
            (this.getFormValue.username.value === this.user.username &&
                this.getFormValue.firstname.value === this.user.firstName &&
                this.getFormValue.lastname.value === this.user.lastName &&
                this.getFormValue.email.value === this.user.email)) {
            alert("Nothing changed");
            return;
        }

        if (this.editUserForm.invalid) {
            return;
        }

        this.loading = true;
        // @ts-ignore
        this.userService.editUser(this.editUserForm, this.user.id)
            .subscribe(response => {
                if (this.getFormValue.username.value === this.user.username) {
                    this.router.navigateByUrl('/users/' + this.user.username);
                } else {
                    this.authentificationService.logout();
                    this.router.navigate(['login']);
                }

            }, error => this.router.navigate(['/error']));
    }

    private setDefaultValues() {
        this.editUserForm.setValue({
            username: this.user.username,
            firstname: this.user.firstName,
            lastname: this.user.lastName,
            email: this.user.email
        });
    }
}


