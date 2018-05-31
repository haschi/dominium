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
import { DebugElement, NO_ERRORS_SCHEMA, Provider } from '@angular/core';
import { InventurModule } from './inventur.module';
import { InventurService } from './inventur.service';
import { NavigatorComponent } from './navigator/navigator.component';
import { ActivatedRoute, Router } from '@angular/router';
import { InventurGruppe } from './shared/gruppen.redux';
import { params$, gruppen$, testgruppen } from './shared/testdaten';
import {Location} from '@angular/common';

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

        navigieren(gruppe: string, kategorie: string): void {
            var navigator = this.navigator[0]
            var index = testgruppen.gruppen[gruppe].kategorien.findIndex(value => {
                return value.kategorie === kategorie
            })

            var link = navigator.query(By.css(`#${gruppe}-${index}`));
            (link.nativeElement as HTMLElement).click()
        }

        showHtml() {
            console.info(this.fixture.nativeElement.innerHTML);
        }
    }

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
                RouterTestingModule.withRoutes(DEMO_APP_ROUTES),
            ],
            providers: [
                Page.provider,
                {provide: InventurService, useValue: {gruppen$}},
                {provide: ActivatedRoute, useValue: {params: params$}}
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
        inject([InventurService, ActivatedRoute],
            (inventur: InventurService, activatedRoute: ActivatedRoute) => {

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
        beforeEach(() => {
            gruppen$.next(testgruppen.gruppen)
        })

        it('sollten vom Navigator angezeigt werden', inject([Page], (page: Page) => {
            var navigator: NavigatorComponent = page.navigator[0].componentInstance
            expect(navigator.gruppen).toEqual(testgruppen.gruppen)
        }))

        it('sollte Inventur kennen', inject([Page], (page: Page) => {
            var navigator: NavigatorComponent = page.navigator[0].componentInstance
            expect(navigator.inventurId).toEqual(params$.value.id)
        }))

        describe('Klick auf Anlagevermögen -> Aktien', () => {
            var spy: jasmine.Spy;

            beforeEach(inject([Page, Router], (page: Page, router: Router) => {
                spy = spyOn(router, 'navigateByUrl');
                page.navigieren('anlagevermoegen', 'Aktien')
            }))

            it('sollte nach /inventur/4711/anlagevermoegen/0 navigieren', () => {
                expect(spy.calls.first().args[0].toString()).toEqual('/inventur/4711/anlagevermoegen/0')
            })
        })

        describe('Klick auf Umlaufvermögen -> Sparbuch', () => {
            var spy: jasmine.Spy;

            beforeEach(inject([Page, Router], (page: Page, router: Router) => {
                spy = spyOn(router, 'navigateByUrl');
                page.navigieren('umlaufvermoegen', 'Sparbuch')
            }))

            it('sollte nach /inventur/4711/umlaufvermoegen/2 navigieren', () => {
                expect(spy.calls.first().args[0].toString()).toEqual('/inventur/4711/umlaufvermoegen/2')
            })
        })

        describe('Klick auf Schulden -> Hypotheken', () => {
            var spy: jasmine.Spy;

            beforeEach(inject([Page, Router], (page: Page, router: Router) => {
                spy = spyOn(router, 'navigateByUrl');
                page.navigieren('schulden', 'Hypotheken')
            }))

            it('sollte nach /inventur/4711/schulden/1 navigieren', () => {
                expect(spy.calls.first().args[0].toString()).toEqual('/inventur/4711/schulden/1')
            })
        })

    })

    describe('Ohne geladene Gruppen', () => {
        beforeEach(() => {
            gruppen$.next(InventurService.leereGruppen)
        })

        it('sollte keinen Navigator anzeigen', inject([Page], (page: Page) => {
            var element = page.navigator;
            expect(page.navigator.length).toBeFalsy()
        }))
    })
});
