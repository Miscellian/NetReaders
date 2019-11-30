import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {ResponseMessage, User} from '../model';
import {FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';



@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private httpClient: HttpClient) { }

    register(registerForm: FormGroup) {
        let user: User = new User();
        user.username = registerForm.value['user_name'];
        user.firstName = registerForm.value['firstName'];
        user.lastName = registerForm.value['lastName'];
        user.email = registerForm.value['email'];
        user.userPassword = registerForm.value['password'];

        return this.httpClient.post(`/users/registration`, user, {observe: 'response'});
    }
}
