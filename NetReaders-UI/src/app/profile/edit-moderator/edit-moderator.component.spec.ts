import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EditModeratorComponent} from './edit-moderator.component';

describe('EditModeratorComponent', () => {
    let component: EditModeratorComponent;
    let fixture: ComponentFixture<EditModeratorComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [EditModeratorComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(EditModeratorComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
