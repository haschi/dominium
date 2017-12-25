import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';

import { InventurComponent } from './inventur.component';
import { AppMaterialModule } from '../shared/app-material-module';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';
import { DEMO_APP_ROUTES } from '../routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HomeComponent } from '../home/home.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LoggerService } from '../shared/logger.service';
import { HttpClientModule } from '@angular/common/http';
import { InventurModule } from './inventur.module';

describe('InventurComponent', () => {
    let component: InventurComponent;
    let fixture: ComponentFixture<InventurComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [HomeComponent],
            imports: [
                NoopAnimationsModule,
                AppMaterialModule,
                AppCovalentModuleModule,
                ReactiveFormsModule,
                CurrencyMaskModule,
                HttpClientModule,
                HttpClientTestingModule,
                RouterTestingModule.withRoutes(DEMO_APP_ROUTES),
                InventurModule
            ],
            providers: [LoggerService],
            schemas: [NO_ERRORS_SCHEMA]

        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(InventurComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    xit ('sollte ohne Eingabe mit leerem Invantar beginnen',
        inject([HttpTestingController], (http: HttpTestingController) => {
        component.speichern();
            const request = http.expectOne('http://localhost:4200/gateway/inventur');
        expect(request.request.method).toEqual('POST');
        expect(request.request.body).toEqual({
            anlagevermoegen: [],
            umlaufvermoegen: [],
            schulden: []
        });
        http.verify();
    }));
});
