import {Component, OnInit} from '@angular/core';
import {Authority, User} from "../../model";
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../user.service";

@Component({
    selector: 'app-add-moderator',
    templateUrl: './add-moderator.component.html',
    styleUrls: ['./add-moderator.component.css']
})
export class AddModeratorComponent implements OnInit {
    user: User;
    addAdminForm: FormGroup;
    submitted = false;
    loading = false;

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private userService: UserService) {

        if (!this.hasAuthority("ADMIN")) {
            this.router.navigate(['/home']);
        }
    }

    get getFormValue() {
        return this.addAdminForm.controls;
    }

    hasAuthority(authority: string): boolean {
        const authorities: Authority[] = JSON.parse(localStorage.getItem('Authorities'));
        if (!authorities) return false;
        return authorities
            .map(val => val.authority)
            .includes(authority);
    }

    passwordValidator(control: FormControl): ValidationErrors {
        const value = control.value;

        const hasNumber = /[0-9]/.test(value);

        const hasCapitalLetter = /[A-Z]/.test(value);

        const hasLowercaseLetter = /[a-z]/.test(value);

        const isLengthValid = value ? value.length > 7 : false;

        const passwordValid = hasNumber && hasCapitalLetter && hasLowercaseLetter && isLengthValid;

        if (!passwordValid) {
            return {invalidPassword: 'Password is invalid'};
        }
        return null;
    }

    ngOnInit() {
        this.userService.getByUsername(localStorage.getItem("UserName")).subscribe(
            response => this.user = response,
            error => this.router.navigate(['error'])
        );
        this.addAdminForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.minLength(3)]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, this.passwordValidator]]
        });
    }

    onSubmit() {
        this.submitted = true;

        if (!this.addAdminForm.touched) {
            alert("Fill all fields");
            return;
        }

        if (this.addAdminForm.invalid) {
            return;
        }

        this.userService.checkIfUsernameExists(this.getFormValue.username.value).subscribe(
            response => {
                if (response === true) {
                    alert("This username already exists, please try another one");
                    return;
                } else {
                    this.userService.checkIfEmailExists(this.getFormValue.email.value).subscribe(
                        response => {
                            if (response === true) {
                                alert("This email already exists, please try another one");
                                return;
                            } else {
                                this.loading = true;
                                this.userService.createAdmin(this.addAdminForm)
                                    .subscribe(
                                        response => this.router.navigate(['/users', this.user.username]),
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
