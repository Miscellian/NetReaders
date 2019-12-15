import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ModeratorsListItemComponent} from './moderators-list-item.component';

describe('ModeratorsListItemComponent', () => {
  let component: ModeratorsListItemComponent;
  let fixture: ComponentFixture<ModeratorsListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ModeratorsListItemComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModeratorsListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
