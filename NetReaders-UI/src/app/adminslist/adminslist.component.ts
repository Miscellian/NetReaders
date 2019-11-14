import { Component, OnInit } from '@angular/core';
import {AdminServiceService} from '../admin-service.service';
import {Router} from '@angular/router';
import {FakeAdmin} from '../userpage/fakeAdmin';

@Component({
  selector: 'app-adminslist',
  templateUrl: './adminslist.component.html',
  styleUrls: ['./adminslist.component.css']
})
export class AdminslistComponent implements OnInit {

  userName: string;
  email: string;
  password: string;
  admin: FakeAdmin;
  constructor(private adminServiceService: AdminServiceService, private router: Router ) { }

  saveAdmin() {
    this.admin = new FakeAdmin(this.userName, this.email, this.password);
    this.adminServiceService.addAdmin(this.admin);
    this.router.navigate(['/userpage']);
  }



  ngOnInit() {
  }

}
