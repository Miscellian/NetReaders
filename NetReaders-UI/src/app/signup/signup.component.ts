import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {AuthenticationService} from '../login/authentication.service';
import {Router} from '@angular/router';
import {UserService} from './user.service';
import {first} from "rxjs/operators";
import {Subject} from "rxjs";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  private subject = new Subject<any>();
  registerForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private authenticationService: AuthenticationService,
      private userService: UserService,
  ) {
    // redirect to home if already logged in
    if (localStorage.getItem('TokenValue')) {
      this.router.navigate(['/']);
    }
  }

  checkPasswords(group: FormGroup) {
    let password = group.value['password'];
    let confirm_password = group.value['confirm_password'];

    return password === confirm_password ? null : {notSame: true}
  }

  private passwordValidator(control: FormControl): ValidationErrors {
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
    this.registerForm = this.formBuilder.group({
      user_name: ['', [Validators.required, Validators.minLength(1)]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]
      ],
      password: ['', [Validators.required, this.passwordValidator]],
      confirm_password: ['', [Validators.required, this.checkPasswords]
      ]
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.userService.register(this.registerForm)
        .subscribe(response => {
          if (!response.isSuccessful) {
            this.router.navigate(['/error']);
            this.loading = false;
          } else {
            this.router.navigate(['/login']);
          }
        });
  }
}