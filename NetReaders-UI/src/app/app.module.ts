import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UserpageComponent } from './userpage/userpage.component';
import { AdminslistComponent } from './adminslist/adminslist.component';
import { SignupComponent} from './signup/signup.component';
import { CommonModule } from '@angular/common';
import { BooksModule } from './books/books.module';
import { LoginComponent } from './login/login.component';
import {AuthGuard} from './_helpers/auth.guard';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ErrorpageComponent } from './errorpage/errorpage.component';

import {AuthenticationService} from './login/authentication.service';
import {UserService} from './signup/user.service';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import {ErrorInterceptor} from './_helpers/error.interceptor';
import {ConfirmUserComponent} from "./confirmUser/confirmUser.component";

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    UserpageComponent,
    AdminslistComponent,
    SignupComponent,
    ErrorpageComponent,
    LoginComponent,
    ConfirmUserComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BooksModule,
    CommonModule,
    NgbModule
  ],
  providers: [
    AuthGuard,
    AuthenticationService,
    UserService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }, 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
