/// <reference path="../shared/http.matcher.d.ts"/>
import { inject, TestBed, async, ComponentFixture } from '@angular/core/testing';
// Load the implementations that should be tested
import { HomeComponent } from './home.component';
import { Title } from './title';
import { By, BrowserModule } from '@angular/platform-browser';
import { HttpTestModule } from '../httptest.module';
import { ReduxTestModule } from '../reduxtest.module';
import { RouterTestingModule } from '@angular/router/testing';
import { AboutComponent } from '../about/about.component';
import { LeererInhaltComponent } from '../leerer-inhalt/leerer-inhalt.component';
import { NoContentComponent } from '../no-content/no-content.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgRedux } from '@angular-redux/store';
import { AppState } from '../reducer';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Response, ResponseOptions, Headers } from '@angular/http';
import { Router } from '@angular/router';
import { httpMatcher } from '../shared/http.matcher';
import Spy = jasmine.Spy;
import { JobService } from '../shared/job.service';
import { AppRouterTestingModule } from '../app-routing.module';

describe('Home', () => {

    describe('Formular', () => {
        beforeAll(() => {
            jasmine.addMatchers(httpMatcher);
        });

        beforeEach(() => TestBed.configureTestingModule({
            declarations: [
                HomeComponent,
                AboutComponent,
                LeererInhaltComponent,
                NoContentComponent
            ],
            imports: [
                HttpTestModule,
                ReduxTestModule,
                ReactiveFormsModule,
                BrowserModule,
                AppRouterTestingModule
            ],
            providers: [
                JobService,
                Title,
                {
                    provide: Page,
                    useFactory: Page.create,
                    deps: [Router]
                }
            ]
        }));

        class Page {
            navigateSpy: Spy;

            static create(router: Router): Page {
                return new Page(TestBed.createComponent(HomeComponent), router);
            }

            constructor(private fixture: ComponentFixture<HomeComponent>, private router: Router) {
                this.navigateSpy = spyOn(this.router, 'navigate');
            }

            init(): void {
                this.fixture.componentInstance.ngOnInit();
                this.fixture.detectChanges();
            }

            input(): void {
                const htmlName = this.fixture.debugElement.query(By.css('#name')).nativeElement;
                htmlName.value = 'Matthias Haschka';
                htmlName.dispatchEvent(new Event('input'));

                this.fixture.detectChanges();
            }

            submit(): void {
                const form = this.fixture.debugElement.query(By.css('form'));
                form.triggerEventHandler('submit', null);
            }

            get name() {
                return this.fixture.componentInstance.name;
            }
        }

        function testfallausführung(
            sut: () => void, testfall: ITestfall, spec: (t: ITestfall) => void) {
            describe(testfall.beschreibung, () => {
                testfall.testfall();
                sut();
                spec(testfall);
            });
        }

        interface ITestfall {
            beschreibung: string;
            testfall: () => void;
            lastConnection: MockConnection;
            ausführen(sut: () => void, spec: (t: ITestfall) => void): void;
        }

        describe('Haushaltsbuchanlage', () => {

            const erfolgreich: ITestfall = {
                lastConnection: null,
                beschreibung: 'falls wirklich erfolgreich',
                ausführen(sut: () => void, spec: (t: ITestfall) => void) {
                    testfallausführung(sut, this, spec);
                },
                testfall() {
                    beforeEach(inject([MockBackend], (backend: MockBackend) => {
                        backend.connections.subscribe((c: MockConnection) => {
                            this.lastConnection = c;
                            c.mockRespond(new Response(new ResponseOptions({
                                status: 202,
                                headers: new Headers({location: '/processstatus/42'})
                            })));
                        });
                    }));
                }
            };

            const haushaltsbuchanlage = () => {
                beforeEach(async(inject([Page], (page: Page) => {
                    page.init();
                    page.input();
                    page.submit();
                })));
            };

            testfallausführung(haushaltsbuchanlage, erfolgreich, t => {
                xit('sollte die Haushaltsbuchanlage anweisen', async(() => {
                    expect(t.lastConnection.request).toPostJson({
                        url: '/api/hauptbuch/haushaltsbuchanlage?asynch=true',
                        body: {name: 'Matthias Haschka'}
                    });
                }));

                xit('sollte neues Haushaltsbuch erzeugen',
                    async(inject([NgRedux], (redux: NgRedux<AppState>) => {
                    expect(redux.getState().haushaltsbuch.id).toEqual(123456);
                })));

                xit('sollte zum Dashboard weiterleiten', async(inject([Page], (page: Page) => {
                    expect(page.navigateSpy).toHaveBeenCalledWith(['/dashboard', 123456]);
                    // TODO: klären, warum navigate zwei mal aufgerufen wird.
                    // expect(page.navigateSpy).toHaveBeenCalledTimes(1);
                })));
            });

            const erfolglos: ITestfall = {
                lastConnection: null,
                beschreibung: 'falls Server nicht verfügbar',
                ausführen(sut: () => void, spec: (t: ITestfall) => void) {
                    testfallausführung(sut, this, spec);
                },
                testfall() {
                    beforeEach(inject([MockBackend], (backend: MockBackend) => {
                        backend.connections.subscribe((c: MockConnection) => {
                            this.lastConnection = c;
                            c.mockError(new Error('error'));
                        });
                    }));
                }
            };

            erfolglos.ausführen(haushaltsbuchanlage, () => {
                it('sollte Verbindungsfehler melden',
                    async(inject([NgRedux], (redux: NgRedux<AppState>) => {
                    expect(redux.getState().verbindung.nachricht).toEqual('Keine Verbindung');
                })));

                it('sollte Kompensationsoption bereitstellen',
                    async(inject([NgRedux], (redux: NgRedux<AppState>) => {
                    expect(redux.getState().verbindung.kompensation.length).toEqual(1);
                })));
            });
        });
    });

    describe('Alles andere ;-)', () => {
        beforeEach(() => TestBed.configureTestingModule({
            declarations: [
                HomeComponent,
                AboutComponent,
                LeererInhaltComponent,
                NoContentComponent
            ],
            providers: [
                Title,
                HomeComponent,
                JobService
            ],
            imports: [
                HttpTestModule,
                ReduxTestModule,
                ReactiveFormsModule,
                BrowserModule,
                RouterTestingModule.withRoutes(ROUTES)
            ]
        }));

        it('should have default data', inject([HomeComponent], (home: HomeComponent) => {
            expect(home.localState).toEqual({value: ''});
        }));

        it('should have a title', inject([HomeComponent], (home: HomeComponent) => {
            expect(!!home.title).toEqual(true);
        }));

        it('should log ngOnInit', inject([HomeComponent], (home: HomeComponent) => {
            spyOn(console, 'log');
            expect(console.log).not.toHaveBeenCalled();

            home.ngOnInit();
            expect(console.log).toHaveBeenCalled();
        }));
    });
});
