import {
  inject,
  TestBed, async, fakeAsync, tick
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

    it('sollte neues Haushaltsbuch anlegen', fakeAsync(() => {
      let fixture = TestBed.createComponent(HomeComponent);
      const spy = spyOn(fixture.componentInstance, 'submitState').and.callThrough();
      fixture.detectChanges();
      fixture.componentInstance.ngOnInit();
      fixture.detectChanges();

      let name = fixture.debugElement.query(By.css('#name'));
      let htmlName = name.nativeElement;
      htmlName.value = "Matthias Haschka";

      htmlName.dispatchEvent(new Event('input'));

      fixture.detectChanges();
      // tick();

      //fixture.whenStable().then(() => {
      let submit = fixture.debugElement.query(By.css('button[type=submit]'));
      let htmlSubmit = submit.nativeElement;
      submit.triggerEventHandler('click', null);

      let form = fixture.debugElement.query( By.css('form'));
      form.triggerEventHandler('submit', null);

      // tick();
      // fixture.detectChanges();
      // tick();
      console.log("Geklickt!");
      expect(spy).toHaveBeenCalled();
      expect(fixture.componentInstance.name).toEqual("Matthias Haschka");
      //});
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
