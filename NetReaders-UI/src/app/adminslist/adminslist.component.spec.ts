import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminslistComponent } from './adminslist.component';

describe('AdminslistComponent', () => {
  let component: AdminslistComponent;
  let fixture: ComponentFixture<AdminslistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminslistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminslistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
