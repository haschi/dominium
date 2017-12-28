import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandProgressComponent } from './command-progress.component';
import { AppMaterialModule } from '../../app-material-module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgReduxModule } from '@angular-redux/store';

describe('CommandProgressComponent', () => {
    let component: CommandProgressComponent;
    let fixture: ComponentFixture<CommandProgressComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CommandProgressComponent],
            imports: [BrowserModule,
                BrowserAnimationsModule,
                AppMaterialModule, NgReduxModule]
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
