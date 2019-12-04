import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ModeratorslistComponent} from './moderatorslist.component';

describe('ModeratorslistComponent', () => {
    let component: ModeratorslistComponent;
    let fixture: ComponentFixture<ModeratorslistComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ModeratorslistComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ModeratorslistComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
