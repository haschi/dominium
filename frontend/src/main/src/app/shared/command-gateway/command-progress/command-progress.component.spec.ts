import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';

import { CommandProgressComponent } from './command-progress.component';
import { AppMaterialModule } from '../../app-material-module';
import { BrowserModule, By } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgReduxModule } from '@angular-redux/store';
import { CommandGatewayService } from '../command-gateway.service';
import { CommandGatewayModule } from '../command-gateway.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CommandType } from '../../../inventur/command-type';
import { StoreModule } from '../../../store/store.module';
import { MatProgressBar } from '@angular/material';

describe('CommandProgressComponent', () => {
    let component: CommandProgressComponent;
    let fixture: ComponentFixture<CommandProgressComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({

            imports: [
                BrowserModule,
                BrowserAnimationsModule,
                AppMaterialModule,
                NgReduxModule,
                CommandGatewayModule,
                HttpClientTestingModule,
                StoreModule
            ]
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

    it('sollte angezeigt werden, wenn ein Command gesendet wird',
        inject([CommandGatewayService], (gateway: CommandGatewayService) => {
            gateway.send(CommandType.BeginneInventur, {}, {});
            fixture.detectChanges();
            expect(fixture.debugElement.query(By.directive(MatProgressBar))).toBeTruthy()
        }));

    it('sollte nicht angezeigt werden, wenn kein Command gesendet wird', () => {
        fixture.detectChanges();
        expect(fixture.debugElement.query(By.directive(MatProgressBar))).toBeFalsy()
    });
});
