import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Authority, EditUser, ModeratorForm, Role, User} from '../model';
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

    createModerator(addModeratorForm: FormGroup, roles: string[]) {
        const user: User = new User();
        const moderatorForm: ModeratorForm = new ModeratorForm();
        user.username = addModeratorForm.controls.username.value;
        user.firstName = null;
        user.lastName = null;
        user.email = addModeratorForm.controls.email.value;
        user.userPassword = addModeratorForm.controls.password.value;
        moderatorForm.user = user;
        moderatorForm.roles = roles;
        return this.httpClient.post(`/users/createModerator`, moderatorForm, {observe: 'response'});
    }

    getRolesForModerator(moderatorUsername: string): Observable<Role[]> {
        return this.httpClient.get<Role[]>(`/users/getRolesForModerator?moderatorUsername=${moderatorUsername}`);
    }

    hasAuthority(authority: string): boolean {
        const authorities: Authority[] = JSON.parse(localStorage.getItem('Authorities'));
        if (!authorities) {
            return false;
        }
        return authorities
            .map(val => val.authority)
            .includes(authority);
    }

    updateModeratorRoles(roles: string[], username: string) {
        const user: User = new User();
        const moderatorForm: ModeratorForm = new ModeratorForm();
        user.username = username;
        moderatorForm.user = user;
        moderatorForm.roles = roles;
        return this.httpClient.post(`/users/updateModeratorRoles`, moderatorForm, {observe: 'response'});
    }
}
