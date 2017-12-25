import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventarComponent } from './inventar.component';

describe('InventarComponent', () => {
    let component: InventarComponent;
    let fixture: ComponentFixture<InventarComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [InventarComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(InventarComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
