import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UserpageComponent } from './profile/userpage/userpage.component';
import { CommonModule } from '@angular/common';
import { BooksModule } from './books/books.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ErrorpageComponent } from './errorpage/errorpage.component';
import { HomepageComponent } from './homepage/homepage.component';
import { UserbooklistComponent } from './profile/userbooklist/userbooklist.component';
import { ProfileComponent } from './profile/profile.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    UserpageComponent,
    ErrorpageComponent,
    HomepageComponent,
    UserbooklistComponent,
    ProfileComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BooksModule,
    CommonModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
