import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators, ValidationErrors, ValidatorFn, FormControl} from '@angular/forms';
import {AuthenticationService} from '../login/authentication.service';
import {Router} from '@angular/router';
import {UserService} from '../profile/user.service';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
    registerForm: FormGroup;
    loading = false;

    constructor(private formBuilder: FormBuilder,
                public router: Router,
                private authenticationService: AuthenticationService,
                private userService: UserService) {
        if (localStorage.getItem('TokenValue')) {
            this.router.navigate(['/']);
        }
    }

    get getFormControls() {
        return this.registerForm.controls;
    }

    get userName() {
        return this.registerForm.get('user_name');
    }

    checkPasswords: ValidatorFn = (group: FormGroup): ValidationErrors | null =>  {
        const password = group.value.password;
        const confirm_password = group.value.confirm_password;

        return password === confirm_password ? null : {'notSame' : true};
    }

    passwordIsValid() {
        const value = this.registerForm.value.password;
        const hasNumber = /[0-9]/.test(value);
        const hasCapitalLetter = /[A-Z]/.test(value);
        const hasLowercaseLetter = /[a-z]/.test(value);
        const isLengthValid = value ? value.length > 7 : false;
        return hasNumber && hasCapitalLetter && hasLowercaseLetter && isLengthValid;
    }

    passwordsMatch() {
        return this.registerForm.value.password === this.registerForm.value.confirm_password;
    }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            user_name: ['', [Validators.required, Validators.minLength(4)]],
            firstName: ['', [Validators.required, Validators.minLength(2)]],
            lastName: ['', [Validators.required, Validators.minLength(2)]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, this.userService.passwordValidator]],
            confirm_password: ['']
        }, {validators: this.checkPasswords});
    }

    onSubmit() {
        const controls = this.registerForm.controls;
        let valid = true;
        for (const name in controls) {
            if (controls[name].invalid) {
                valid = false;
            }
        }
        if (!valid || this.registerForm.invalid) {
            return;
        }

        this.userService.checkIfUsernameExists(this.registerForm.value.username).subscribe(
            userNameExists => {
                if (userNameExists) {
                    alert('This username already exists, please try another one');
                    return;
                } else {
                    this.userService.checkIfEmailExists(this.registerForm.value.email).subscribe(
                        emailExists => {
                            if (emailExists) {
                                alert('This email already exists, please try another one');
                                return;
                            } else {
                                this.loading = true;
                                this.userService.register(this.registerForm).subscribe(
                                    () => this.router.navigate(['/login']),
                                    error => this.router.navigate(['/error'])
                                );
                            }
                        },
                        error => this.router.navigate(['/error']));
                }
            },
            error => this.router.navigate(['/error']));
    }
}
