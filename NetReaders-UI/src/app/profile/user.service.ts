import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {EditUser, User} from '../model';
import {FormGroup} from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private httpClient: HttpClient) {
    }

    getByUsername(username: string): Observable<User> {
        return this.httpClient.get<User>(`/users/${username}`);
    }

    getAdminsList(): Observable<User[]> {
        return this.httpClient.get<User[]>(`/users/getAdminsList`);
    }

    getModeratorsList(): Observable<User[]> {
        return this.httpClient.get<User[]>(`/users/getModeratorsList`);
    }

    editUser(editUserForm: FormGroup, userId: number) {
        const editUser: EditUser = new EditUser();
        editUser.email = editUserForm.controls.email.value;
        editUser.username = editUserForm.controls.username.value;
        editUser.firstname = editUserForm.controls.firstname.value;
        editUser.lastname = editUserForm.controls.lastname.value;
        editUser.userId = userId;
        return this.httpClient.post(`/users/editUser`, editUser, {observe: 'response'});
    }

    register(registerForm: FormGroup) {
        const user: User = new User();
        user.username = registerForm.value.user_name;
        user.firstName = registerForm.value.firstName;
        user.lastName = registerForm.value.lastName;
        user.email = registerForm.value.email;
        user.userPassword = registerForm.value.password;

        return this.httpClient.post(`/users/registration`, user, {observe: 'response'});
    }

    checkIfUsernameExists(username: string): Observable<boolean> {
        return this.httpClient.get<boolean>(`/users/checkIfUsernameExists?username=${username}`);
    }

    checkIfEmailExists(email: string): Observable<boolean> {
        return this.httpClient.get<boolean>(`/users/checkIfEmailExists?email=${email}`);
    }

    removeAdmin(admin: User) {
        return this.httpClient.post(`/users/removeAdmin`, admin, {observe: 'response'});
    }

    createAdmin(addAdminForm: FormGroup) {
        const user: User = new User();
        user.username = addAdminForm.controls.username.value;
        user.firstName = null;
        user.lastName = null;
        user.email = addAdminForm.controls.email.value;
        user.userPassword = addAdminForm.controls.password.value;
        return this.httpClient.post(`/users/createAdmin`, user, {observe: 'response'});
    }

    removeModerator(moderator: User) {
        return this.httpClient.post(`/users/removeModerator`, moderator, {observe: 'response'});
    }
}
