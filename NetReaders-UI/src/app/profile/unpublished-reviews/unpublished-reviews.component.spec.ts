import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnpublishedReviewsComponent } from './unpublished-reviews.component';

describe('UnpublishedReviewsComponent', () => {
  let component: UnpublishedReviewsComponent;
  let fixture: ComponentFixture<UnpublishedReviewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnpublishedReviewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnpublishedReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
