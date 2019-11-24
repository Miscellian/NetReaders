import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: FormGroup;
  constructor(private fb: FormBuilder){}
  ngOnInit(){
    this.initForm();
  }
  private passwordValidator(control: FormControl): ValidationErrors {
    const value = control.value;

    const hasNumber = /[0-9]/.test(value);

    const hasCapitalLetter = /[A-Z]/.test(value);

    const hasLowercaseLetter = /[a-z]/.test(value);

    const isLengthValid = value ? value.length > 7 : false;


    const passwordValid = hasNumber && hasCapitalLetter && hasLowercaseLetter && isLengthValid;

    if (!passwordValid) {
      return { invalidPassword: 'Password is invalid' };
    }
    return null;
  }

  private initForm(): void{
    this.user =this.fb.group({
      user_name: ['',[
        Validators.required,
        Validators.minLength(1)]
      ],
      password: ['',[
        Validators.required,
        this.passwordValidator]
      ],
    });
  }

  onSubmit(){
    const controls = this.user.controls;

    if (this.user.invalid) {

      Object.keys(controls)
          .forEach(controlName => controls[controlName].markAsTouched());
      return;
    }

    /** TODO: Обработка данных формы */
    console.log(this.user.value);
  }

}
