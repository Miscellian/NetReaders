import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BooklistItemComponent } from './booklist-item.component';

describe('BooklistItemComponent', () => {
  let component: BooklistItemComponent;
  let fixture: ComponentFixture<BooklistItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BooklistItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BooklistItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
