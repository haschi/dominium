import { DebugElement, NO_ERRORS_SCHEMA, Provider } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { HomeComponent } from '../home/home.component';
import { DEMO_APP_ROUTES } from '../routes';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';
import { AppMaterialModule } from '../shared/app-material-module';
import { BilanzComponent } from './bilanz/bilanz.component';
import { PostenComponent } from './gruppe/posten/posten.component';
import { InventarComponent } from './inventar/inventar.component';
import { InventurComponent } from './inventur.component';
import { InventurService } from './inventur.service';
import { NavigatorComponent } from './navigator/navigator.component';
import { Eingabe } from './shared/inventar-eingabe.redux';
import { PositionEingabe } from './shared/inventarposition';
import { gruppen$, params$, testgruppen } from './shared/testdaten';

fdescribe('InventurComponent', () => {
    let component: InventurComponent;
    let fixture: ComponentFixture<InventurComponent>;

    let eingabe$ = new BehaviorSubject<Eingabe[]>([])

    class Page {
        constructor(public fixture: ComponentFixture<InventurComponent>) {
        }

        static create(): Page {
            const page = new Page(TestBed.createComponent(InventurComponent));
            page.fixture.detectChanges();

            return page;
        }

        static provider: Provider = {provide: Page, useFactory: Page.create}

        get navigator(): DebugElement {
            return this.fixture.debugElement.query(By.directive(NavigatorComponent))
        }

        get posten(): PostenComponent[] {
            return this.fixture.debugElement
                .queryAll(By.directive(PostenComponent))
                .map(element => element.componentInstance as PostenComponent)
        }

        get position(): HTMLInputElement {
            const element: HTMLElement = this.fixture.debugElement.nativeElement
            return element.querySelector('#position')
        }

        get hinzufügen(): DebugElement {
            return this.fixture.debugElement
                .query(By.css('#hinzufuegen'))
        }

        navigieren(gruppe: string, kategorie: string): void {
            var navigator = this.navigator
            var index = testgruppen.gruppen[gruppe].kategorien.findIndex(value => {
                return value.kategorie === kategorie
            })

            var link = navigator.query(By.css(`#${gruppe}-${index}`));
            (link.nativeElement as HTMLElement).click()
        }

        enter(selector: string, wert: string) {
            const element: HTMLElement = this.fixture.debugElement.nativeElement
            const input: HTMLInputElement = element.querySelector(selector)

            input.value = wert;
            input.dispatchEvent(new Event('input'))
        }

        eingeben(position: string, betrag: string, waehrung: string) {
            this.enter('#position', position);
            this.enter('#betrag', betrag);
            this.enter('#waehrung', waehrung)
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
                NavigatorComponent,
                PostenComponent
            ],
            imports: [
                NoopAnimationsModule,
                AppMaterialModule,
                AppCovalentModuleModule,
                RouterTestingModule.withRoutes(DEMO_APP_ROUTES),
            ],
            providers: [
                Page.provider,
                {provide: InventurService, useValue: {gruppen$, eingabe$: (gruppe, kategorie) => {return eingabe$}}},
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
            var navigator: NavigatorComponent = page.navigator.componentInstance
            expect(navigator.gruppen).toEqual(testgruppen.gruppen)
        }))

        it('sollte Inventur kennen', inject([Page], (page: Page) => {
            var navigator: NavigatorComponent = page.navigator.componentInstance
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
            expect(page.navigator).toBeFalsy()
        }))
    })

    describe('Keine Kategorie ausgewählt', () => {
        beforeEach(() => {
            params$.next({id: '4711', gruppe: null, kategorie: null})
        })

        it('sollte Schaltfläche Hinzufügen nicht anzeigen', inject([Page], (page: Page) => {
            page.fixture.detectChanges()
            expect(page.hinzufügen).toBeFalsy()
        }))
    })

    describe('Kategorie Anlagevermögen -> Aktien ausgewählt', () => {

        beforeEach(() => {
            params$.next({id: '4711', gruppe: 'anlagevermoegen', kategorie: 0})
            eingabe$.next([])
        })

        describe('ohne vorangegangene Eingabe von Posten', () => {
            it('sollte leere Liste anzeigen', inject([Page], (page: Page) => {
                page.fixture.detectChanges()
                expect(page.posten).toEqual([])
            }))
        })

        describe('mit Eingabe des Postens VW, 10.000,00 EUR', () => {
            beforeEach(() => {
                eingabe$.next([{
                    gruppe: 'anlagevermoegen',
                    kategorie: 0,
                    position: {
                        position: 'VW',
                        waehrungsbetrag: {
                            betrag: '10.000,00',
                            waehrung: 'EUR'
                        }
                    }
                }])
            })

            it('sollte Eingabe in Liste anzeigen', inject([Page], (page: Page) => {
                function istEingabe(p, eingabe: PositionEingabe) {
                    return p.posten.position === eingabe.position
                        && p.posten.waehrungsbetrag.betrag === eingabe.waehrungsbetrag.betrag
                        && p.posten.waehrungsbetrag.waehrung === eingabe.waehrungsbetrag.waehrung;
                }

                const gesucht = page.posten.find(p => istEingabe(p, {
                    position: 'VW',
                    waehrungsbetrag: {betrag: '10.000,00', waehrung: 'EUR'}
                }))

                expect(gesucht).not.toBeUndefined()
            }))
        })

        describe('Hinzufügen Schaltfläche', () => {
            it('sollte Eingabe-Dialog öffnen', inject([Page], (page: Page) => {
                expect(page.hinzufügen).toBeTruthy()
            }))
        })
    })
});
