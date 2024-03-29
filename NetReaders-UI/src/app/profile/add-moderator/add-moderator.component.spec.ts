import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddModeratorComponent} from './add-moderator.component';

describe('AddModeratorComponent', () => {
    let component: AddModeratorComponent;
    let fixture: ComponentFixture<AddModeratorComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AddModeratorComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AddModeratorComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
