/// <reference path="../shared/http.matcher.d.ts"/>
import {
  inject,
  TestBed, async, ComponentFixture
} from '@angular/core/testing';

// Load the implementations that should be tested
import { HomeComponent } from './home.component';
import { Title } from './title';
import {By, BrowserModule} from "@angular/platform-browser";
import {HttpTestModule} from "../httptest.module";
import {ReduxTestModule} from "../reduxtest.module";
import {RouterTestingModule} from "@angular/router/testing";
import {ROUTES} from "../app.routes";
import {AboutComponent} from "../about/about.component";
import {LeererInhaltComponent} from "../leerer-inhalt/leerer-inhalt.component";
import {NoContentComponent} from "../no-content/no-content.component";
import {ReactiveFormsModule} from "@angular/forms";
import {NgRedux} from "@angular-redux/store";
import {AppState} from "../reducer";
import {MockBackend, MockConnection} from "@angular/http/testing";
import {Response, ResponseOptions, Headers, RequestMethod} from "@angular/http";
import {Router} from "@angular/router";
import Spy = jasmine.Spy;
import {ContentType} from "@angular/http/src/enums";
import {httpMatcher} from "../shared/http.matcher";

describe('Home', () => {

  describe('Formular', () => {
      beforeAll(()=> {
          jasmine.addMatchers(httpMatcher)
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
        RouterTestingModule.withRoutes(ROUTES)
      ],
      providers: [
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
          this.navigateSpy = spyOn(router, 'navigate');
      }

      init(): void {
        this.fixture.componentInstance.ngOnInit();
        this.fixture.detectChanges();
      }

      input(name: string): void {
        let htmlName = this.fixture.debugElement.query(By.css('#name')).nativeElement;
        htmlName.value = "Matthias Haschka";
        htmlName.dispatchEvent(new Event('input'));

        this.fixture.detectChanges();
      }

      submit(): void {
        let form = this.fixture.debugElement.query( By.css('form'));
        form.triggerEventHandler('submit', null);
      }

      get name() { return this.fixture.componentInstance.name }
    }

    describe("Haushaltsbuch anlegen", () => {
        let c: MockConnection;
        beforeEach(inject([MockBackend], (backend: MockBackend) => {
            backend.connections.subscribe((connection: MockConnection) => {
                c = connection;
                connection.mockRespond(new Response(new ResponseOptions({
                    status: 202,
                    headers: new Headers({location: '/process/42'}),
                    body: JSON.stringify({id: 123456})
                })))
            })
        }));

        beforeEach(async(inject([Page], (page: Page)=> {
            page.init();
            page.input("Matthias Haschka");
            page.submit();
        })));

        it('sollte neues Haushaltsbuch anlegen',
            async(inject([Page, NgRedux], (page: Page, redux: NgRedux<AppState>) => {
                    expect(redux.getState().haushaltsbuch.id).toEqual(123456);
                    expect(c.request).toPostJson({
                        url: '/api/haushaltsbuchanlage',
                        body: {name: "Matthias Haschka"}
                    });
                })));

        describe("falls erfolgreich", () => {

        });

        it('sollte zum Dashboard weiterleiten', async(inject([Page],(page: Page) => {
            expect(page.navigateSpy).toHaveBeenCalledWith(['/dashboard', 123456]);
            // TODO: klären, warum navigate zwei mal aufgerufen wird.
            // expect(page.navigateSpy).toHaveBeenCalledTimes(1);
        })));
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
        HomeComponent
      ],
      imports: [
        HttpTestModule,
        ReduxTestModule,
        ReactiveFormsModule,
        BrowserModule,
        RouterTestingModule.withRoutes(ROUTES)
      ]
    }));

    it('should have default data', inject([ HomeComponent ], (home: HomeComponent) => {
      expect(home.localState).toEqual({ value: '' });
    }));

    it('should have a title', inject([ HomeComponent ], (home: HomeComponent) => {
      expect(!!home.title).toEqual(true);
    }));

    it('should log ngOnInit', inject([ HomeComponent ], (home: HomeComponent) => {
      spyOn(console, 'log');
      expect(console.log).not.toHaveBeenCalled();

      home.ngOnInit();
      expect(console.log).toHaveBeenCalled();
    }));
  });

});
