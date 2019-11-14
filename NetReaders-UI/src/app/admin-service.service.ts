import { Injectable } from '@angular/core';
import {FakeAdmin, fakeAdminList} from './userpage/fakeAdmin';


@Injectable({
  providedIn: 'root'
})
export class AdminServiceService {

  allAdmins = fakeAdminList;

getAllAdmins(): FakeAdmin[] {
  return this.allAdmins;
}

addAdmin(admin: FakeAdmin): void {
  this.allAdmins.push(admin);
}

  constructor() { }
}
