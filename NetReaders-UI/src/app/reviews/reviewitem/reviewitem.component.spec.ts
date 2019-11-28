import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewitemComponent } from './reviewitem.component';

describe('ReviewitemComponent', () => {
  let component: ReviewitemComponent;
  let fixture: ComponentFixture<ReviewitemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewitemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewitemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
