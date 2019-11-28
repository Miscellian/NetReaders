import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookviewComponent} from './books/bookview/bookview.component';
import {BooklistComponent} from './books/booklist/booklist.component';
import { SignupComponent } from './signup/signup.component';
import {ErrorpageComponent} from './errorpage/errorpage.component';
import { LoginComponent } from './login/login.component';
import {ConfirmUserComponent} from "./confirmUser/confirmUser.component";
import {HomepageComponent} from "./homepage/homepage.component";
import {ProfileComponent} from "./profile/profile.component";


const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: HomepageComponent},
    {path: 'users/:username', component: ProfileComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'login', component: LoginComponent},
  {path: 'books',
        children: [
            {path: ':id', component: BookviewComponent},
            {path: 'byauthor/:id/:page', component: BooklistComponent, data: {filter: 'author'}},
            {path: 'bygenre/:id/:page', component: BooklistComponent, data: {filter: 'genre'}},
            {path: 'byname/:id/:page', component: BooklistComponent, data: {filter: 'name'}},
            {path: 'range/:page', component: BooklistComponent, data: {filter: 'range'}},
            {path: 'byuser/:id/:page', component: BooklistComponent, data: {filter: 'user'}}
        ]
    },
  {path: 'confirmUser', component: ConfirmUserComponent},
    {path: 'error', component: ErrorpageComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
