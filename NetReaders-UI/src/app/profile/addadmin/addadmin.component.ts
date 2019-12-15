import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {User} from '../../model';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
    selector: 'app-addadmin',
    templateUrl: './addadmin.component.html',
    styleUrls: ['./addadmin.component.css']
})
export class AddAdminComponent implements OnInit {
    user: User;
    addAdminForm: FormGroup;
    submitted = false;
    loading = false;

    constructor(private router: Router,
                private formBuilder: FormBuilder,
                private userService: UserService) {

        if (!this.userService.hasAuthority('SUPER_ADMIN')) {
            this.router.navigate(['/home']);
        }
    }

    get getFormValue() {
        return this.addAdminForm.controls;
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
        this.userService.getByUsername(localStorage.getItem('UserName')).subscribe(
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
            alert('Fill all fields');
            return;
        }

        if (this.addAdminForm.invalid) {
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
                                this.userService.createAdmin(this.addAdminForm)
                                    .subscribe(
                                        () => this.router.navigate(['/users', this.user.username]),
                                        () => this.router.navigate(['/error']));
                            }
                        },
                        () => this.router.navigate(['/error'])
                    );
                }
            },
            () => this.router.navigate(['/error']));
    }
}
