import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
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
    submitted = false;

    constructor(
        private formBuilder: FormBuilder,
        public router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService,
    ) {
        // redirect to home if already logged in
        if (localStorage.getItem('TokenValue')) {
            this.router.navigate(['/']);
        }
    }

    // convenience getter for easy access to form fields
    get getFormControls() {
        return this.registerForm.controls;
    }

    checkPasswords(group: FormGroup) {
        const password = group.value.password;
        const confirm_password = group.value.confirm_password;

        return password === confirm_password ? null : {notSame: true};
    }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            user_name: ['', [Validators.required, Validators.minLength(4)]],
            firstName: ['', Validators.required, Validators.minLength(2)],
            lastName: ['', Validators.required, Validators.minLength(2)],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, this.userService.passwordValidator]],
            confirm_password: ['', [Validators.required, this.checkPasswords]]
        });
    }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }

        this.loading = true;
        this.userService.register(this.registerForm)
            .subscribe(
                () => this.router.navigate(['/login']),
                error => this.router.navigate(['/error'])
            );
    }
}
