import { Component, OnInit } from '@angular/core';
import {FakeAdmin, fakeAdminList} from './fakeAdmin';
import {AdminServiceService} from '../admin-service.service';
@Component({
  selector: 'app-userpage',
  templateUrl: './userpage.component.html',
  styleUrls: ['./userpage.component.css']
})
export class UserpageComponent implements OnInit {

  adminList: FakeAdmin[];
  constructor(private adminService: AdminServiceService) {
  }

  ngOnInit() {
    this.adminList = this.adminService.getAllAdmins();
  }

}
