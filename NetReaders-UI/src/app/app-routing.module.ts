import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserpageComponent } from './userpage/userpage.component';
import { AdminslistComponent } from './adminslist/adminslist.component';


const routes: Routes = [
  {path: '',  redirectTo: '/userpage', pathMatch: 'full' },
  {path: 'userpage', component: UserpageComponent},
  {path: 'newadmin', component: AdminslistComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
