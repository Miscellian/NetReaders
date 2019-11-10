import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UserpageComponent } from './userpage/userpage.component';
import { AdminslistComponent } from './adminslist/adminslist.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    UserpageComponent,
    AdminslistComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
