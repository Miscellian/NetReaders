import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewviewComponent } from './reviewview.component';

describe('ReviewviewComponent', () => {
  let component: ReviewviewComponent;
  let fixture: ComponentFixture<ReviewviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
