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
import { InventurService } from './inventur.service';
import { CommandGatewayModule } from '../shared/command-gateway/command-gateway.module';
import { StoreModule } from '../store/store.module';
import { CommandType } from './command-type';

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
                InventurModule,
                CommandGatewayModule,
                StoreModule
            ],
            providers: [LoggerService, InventurService],
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

    it('sollte ohne Eingabe mit leerem Invantar beginnen',
        inject([HttpTestingController, InventurService], (http: HttpTestingController, inventur: InventurService) => {

            inventur.beginneInventur('12345');
            const beginne = http.expectOne('/gateway/command');
            expect(beginne.request.method).toEqual('POST');
            expect(beginne.request.body).toEqual(
                {
                    type: CommandType.BeginneInventur,
                    payload: {id: '12345'},
                    meta: {}
                });
            beginne.flush(null);

            inventur.inventurid$.subscribe(id => expect(id).toEqual('12345'));

            component.speichern();

            const erfasse = http.expectOne('/gateway/command');
            expect(erfasse.request.method).toEqual('POST');
            expect(erfasse.request.body).toEqual(
                {
                    type: CommandType.ErfasseInventar,
                    payload: {
                        id: "12345",
                        inventar: {
                            anlagevermoegen: [],
                            umlaufvermoegen: [],
                            schulden: []
                        }
                    },
                    meta: {}
                });

            inventur.inventar$.subscribe(inventar => expect(inventar).toEqual({
                anlagevermoegen: [],
                umlaufvermoegen: [],
                schulden: []
            }));

        http.verify();
    }));
});
