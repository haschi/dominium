import { NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { BrowserModule, By } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { of } from 'rxjs/observable/of';
import { AppCovalentModuleModule } from '../../shared/app-covalent-module.module';
import { AppMaterialModule } from '../../shared/app-material-module';
import { INVENTUR_INITIAL_STATE} from '../inventur.redux';
import { BilanzComponent } from '../bilanz/bilanz.component';
import { Inventar } from '../inventar';
import { InventurState } from '../inventur.redux';
import { InventurService } from '../inventur.service';
import { InventarComponent } from './inventar.component';
import { PositionComponent } from './position/position.component';
import { ZeileComponent } from './zeile/zeile.component';

describe('InventarComponent', () => {

    class Page {
        constructor(public fixture: ComponentFixture<InventarComponent>) {
        }

        static create(): Page {
            return new Page(TestBed.createComponent(InventarComponent))
        }

        navigieren(): void {
            this.fixture.detectChanges();
            const link = this.fixture.debugElement.query(By.css('#bilanz'));
            if (!link) throw "Unbekanntes Element";
            link.nativeElement.click()
        }
    }

    const vollesInventar: Inventar = {
        anlagevermoegen: [{
            position: "Aktiendepot",
            waehrungsbetrag: "10.000,00 EUR"
        }],
        umlaufvermoegen: [{
            position: "Girokonto",
            waehrungsbetrag: "5.000,00 EUR"
        }],
        schulden: [{
            position: "Autokredit",
            waehrungsbetrag: "1.000,00 EUR"
        }],
        reinvermoegen: {
            summeDesVermoegens: "15.000,00 EUR",
            summeDerSchulden: "1.000,00 EUR",
            reinvermoegen: "14.000,00 EUR"
        }
    };

    const mock = {
        leseInventar: jasmine.createSpy('leseInventar'),
        inventar$: of(vollesInventar),
        inventurid$: new BehaviorSubject<string>('4711'),
        state$: new BehaviorSubject<InventurState>(INVENTUR_INITIAL_STATE)
    };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                InventarComponent,
                ZeileComponent,
                PositionComponent,
                BilanzComponent
            ],
            imports: [
                BrowserModule,
                BrowserAnimationsModule,
                AppMaterialModule,
                AppCovalentModuleModule,
                RouterTestingModule.withRoutes([
                    {path: 'inventur/bilanz/:id', component: BilanzComponent}
                ])
            ],
            providers: [
                {provide: Page, useFactory: Page.create},
                {provide: InventurService, useValue: mock}
            ],
            schemas: [NO_ERRORS_SCHEMA]
        })
            .compileComponents();
    }));

    it('should create', inject([Page], (page: Page) => {
        expect(page.fixture).toBeTruthy();
    }));

    ['4712', '4713'].forEach(id => {
        describe(`Für die Inventur mit der id ${id}`, () => {
            beforeEach(() => {
                mock.state$.next({...INVENTUR_INITIAL_STATE, inventurId: id})
            });

            // Testmuster: Navigation mit Router#navigate
            it(`sollte zur Eröffnungsbilanz ${id} weiterleiten`,
                async(inject([Page, Router], (page: Page, router: Router) => {
                    page.fixture.detectChanges()
                    const spy = spyOn(router, 'navigate');
                    page.navigieren();
                })))
        });
    });
});
