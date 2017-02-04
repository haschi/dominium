import {
  inject,
  TestBed, async, fakeAsync, tick, ComponentFixture
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
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

describe('Home', () => {

  describe('Formular', () => {
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
        Title
      ]
    }));

    class Page {
      constructor(private fixture: ComponentFixture<HomeComponent>) {}

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

    it('sollte neues Haushaltsbuch anlegen', fakeAsync(() => {
      let fixture = TestBed.createComponent(HomeComponent);
      let page = new Page(fixture);
      page.init();
      page.input("Matthias Haschka");
      page.submit();

      console.log("Geklickt!");
      expect(page.name).toEqual("Matthias Haschka");
    }));
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
