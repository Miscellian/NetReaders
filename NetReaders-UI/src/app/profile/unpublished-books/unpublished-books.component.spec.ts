import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UnpublishedBooksComponent} from './unpublished-books.component';

describe('UnpublishedBooksComponent', () => {
    let component: UnpublishedBooksComponent;
    let fixture: ComponentFixture<UnpublishedBooksComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UnpublishedBooksComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(UnpublishedBooksComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
