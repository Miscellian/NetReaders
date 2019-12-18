import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from './authentication.service';
import {UserService} from "../profile/user.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService
    ) {
        // redirect to home if already logged in
        if (localStorage.getItem('TokenValue')) {
            this.router.navigate(['/']);
        }
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });

        // get return url from route parameters or default to '/'
        // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() {
        return this.loginForm.controls;
    }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.loading = true;
        this.authenticationService.login(this.f.username.value, this.f.password.value)
            .subscribe(response => {
                const username = localStorage.getItem('UserName');
                this.router.navigateByUrl('/users/' + username);
            }, error => {
                if (error.status === 422) {
                    alert('Invalid credentials');
                    this.loading = false;
                    return;
                }
                if (error.error === 'User doesn\'t exists') {
                    alert('No such user');
                    this.loading = false;
                    return;
                }
                if (error.error === 'Finish your registration first') {
                    alert('Confirm your email first');
                    this.loading = false;
                    return;
                }
                this.router.navigate(['/error']);
            });

    }
}
