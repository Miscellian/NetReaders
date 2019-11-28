import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserbooklistComponent } from './userbooklist.component';

describe('UserbooklistComponent', () => {
  let component: UserbooklistComponent;
  let fixture: ComponentFixture<UserbooklistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserbooklistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserbooklistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
