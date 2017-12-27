import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandProgressComponent } from './command-progress.component';

describe('CommandProgressComponent', () => {
    let component: CommandProgressComponent;
    let fixture: ComponentFixture<CommandProgressComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CommandProgressComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CommandProgressComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
