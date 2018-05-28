import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { BilanzComponent } from './bilanz/bilanz.component';
import { InventarComponent } from './inventar/inventar.component';

import { InventurComponent } from './inventur.component';
import { AppMaterialModule } from '../shared/app-material-module';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';
import { DEMO_APP_ROUTES } from '../routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HomeComponent } from '../home/home.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { DebugElement, NO_ERRORS_SCHEMA, Provider } from '@angular/core';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LoggerService } from '../shared/logger.service';
import { HttpClientModule } from '@angular/common/http';
import { InventurModule } from './inventur.module';
import { InventurService } from './inventur.service';
import { CommandGatewayModule } from '../shared/command-gateway/command-gateway.module';
import { StoreModule } from '../store/store.module';
import { NavigatorComponent } from './navigator/navigator.component';
import { CommandType } from './shared/command-type';
import { ActivatedRouteStub } from './activated-route-stub';
import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/observable/of';
import { InventurGruppe } from './shared/gruppen.redux';
import { InventarEingabeService } from './shared/inventar-eingabe.service';
import { state, testgruppen } from './shared/testdaten';

describe('InventurComponent', () => {
    let component: InventurComponent;
    let fixture: ComponentFixture<InventurComponent>;

    class Page {
        constructor(public fixture: ComponentFixture<InventurComponent>) {
        }

        static create(): Page {
            const page = new Page(TestBed.createComponent(InventurComponent));
            page.fixture.detectChanges();

            return page;
        }

        static provider: Provider = {provide: Page, useFactory: Page.create}

        // Siehe https://stackoverflow.com/questions/44301315/karma-error-no-captured-browser-open-http-localhost9876/48606192
        get navigator(): DebugElement[] {
            return this.fixture.debugElement.queryAll(By.directive(NavigatorComponent))
        }

        showHtml() {
            console.info(this.fixture.nativeElement.innerHTML);
        }
    }

    var testgruppen$ = new BehaviorSubject(state.gruppen)

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                InventurComponent,
                BilanzComponent,
                InventarComponent,
                HomeComponent,
                NavigatorComponent
            ],
            imports: [
                NoopAnimationsModule,
                AppMaterialModule,
                AppCovalentModuleModule,
                ReactiveFormsModule,
                CurrencyMaskModule,
                HttpClientModule,
                HttpClientTestingModule,
                RouterTestingModule.withRoutes(DEMO_APP_ROUTES),
                // InventurModule,
                CommandGatewayModule,
                StoreModule
            ],
            providers: [
                LoggerService,
                Page.provider,
                {provide: InventurService, useValue: {gruppen$: testgruppen$}},
                {provide: ActivatedRoute, useValue: {params: Observable.of({id: '4567', gruppe: 'schulden', kategorie: 0})}},
                {provide: InventarEingabeService, useValue: testgruppen$}
            ],
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

    it('sollte ohne Eingabe mit leerem Inventar beginnen',
        inject([HttpTestingController, InventurService, ActivatedRoute],
            (http: HttpTestingController, inventur: InventurService, activatedRoute: ActivatedRoute) => {

            // (activatedRoute as ActivatedRouteStub).setParamMap({id: '12345'});
            // inventur.beginneInventur('12345');

            // const beginne = http.expectOne('/gateway/command');
            // expect(beginne.request.method).toEqual('POST');
            // expect(beginne.request.body).toEqual(
            //     {
            //         type: CommandType.BeginneInventur,
            //         payload: {id: '12345'},
            //         meta: {}
            //     });
            // beginne.flush(null);

            // inventur.inventurid$.subscribe(id => expect(id).toEqual('12345'));

            //component.speichern();

        //     const erfasse = http.expectOne('/gateway/command');
        //     expect(erfasse.request.method).toEqual('POST');
        //     expect(erfasse.request.body).toEqual(
        //         {
        //             type: CommandType.ErfasseInventar,
        //             payload: {
        //                 id: "12345",
        //                     anlagevermoegen: [],
        //                     umlaufvermoegen: [],
        //                     schulden: [],
        //             },
        //             meta: {}
        //         });
        //
        // http.verify();
    }));

    describe('Geladene Gruppen', () => {
        beforeEach(inject([InventarEingabeService], (service: InventarEingabeService) => {
            testgruppen$.next(testgruppen.gruppen)
        }))

        it('sollten vom Navigator angezeigt werden', inject([Page], (page: Page) => {
            var navigator: NavigatorComponent = page.navigator[0].componentInstance
            expect(navigator.gruppen).toEqual(testgruppen.gruppen)
        }))
    })

    describe('Ohne geladene Gruppen', () => {
        beforeEach(inject([InventarEingabeService], (service: InventarEingabeService) => {
            testgruppen$.next(state.gruppen)
        }))

        it('sollte keinen Navigator anzeigen', inject([Page], (page: Page) => {

            var element = page.navigator;
            console.log(element)
            expect(page.navigator.length).toBeFalsy()
        }))

        it('sollte erkennen, dass testdaten und init state gleich sind', () => {
            expect(InventurService.leereGruppen).toEqual(state.gruppen)
        })
    })
});
