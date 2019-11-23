import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
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

  checkPasswords(group: FormGroup) {
    let password = group.get('password').value;
    let confirm_password = group.get('confirm_password').value;

    return password === confirm_password ? null : { notSame: true }
  }
  
  private initForm(): void{
    this.user =this.fb.group({
      user_name: ['',[
          Validators.required,
          Validators.minLength(1)]
      ],
      first_name: ['',[
        Validators.required,
        Validators.minLength(1)]
      ],
      last_name: ['',[
        Validators.required,
        Validators.minLength(1)]
      ],
      email: ['',[
        Validators.required,
        Validators.email]
      ],
      password: ['',[
        Validators.required,
        this.passwordValidator]
      ],
      confirm_password: ['',[
        Validators.required,
        this.checkPasswords]
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
