import { NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { BrowserModule, By } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { of } from 'rxjs/observable/of';
import { Subject } from 'rxjs/Subject';
import { AppCovalentModuleModule } from '../../shared/app-covalent-module.module';
import { AppMaterialModule } from '../../shared/app-material-module';
import { BilanzComponent } from '../bilanz/bilanz.component';
import { Inventar } from '../inventar';

import { InventurService } from '../inventur.service';

import { InventarComponent } from './inventar.component';
import { PositionComponent } from './position/position.component';
import { ZeileComponent } from './zeile/zeile.component';

fdescribe('InventarComponent', () => {

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

    const leeresInventar: Inventar = {
        anlagevermoegen: [{position: "Aktiendepot", waehrungsbetrag: "10.000,00 EUR"}],
        umlaufvermoegen: [],
        schulden: [],
        reinvermoegen: {
            summeDesVermoegens: "0,00 EUR",
            summeDerSchulden: "0,00 EUR",
            reinvermoegen: "0,00 EUR"
        }
    };

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
        inventurid$: new BehaviorSubject<string>('4711')
    };

    const inventurServiceMock = jasmine.createSpyObj(
        'InventurService',
        ['beginneInventur', 'beginnen', 'erfasseInventar', 'leseInventar']);

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
                mock.inventurid$.next(id)
            });

            // Testmuster: Navigation mit Router Link
            it(`sollte zur Eröffnungsbilanz ${id} weiterleiten`,
                async(inject([Page, Router], (page: Page, router: Router) => {
                    const spy = spyOn(router, 'navigateByUrl');
                    const url = router.createUrlTree(['inventur', 'bilanz', id]);
                    page.navigieren();

                    expect(spy).toHaveBeenCalledWith(url, jasmine.anything())
                })))
        });
    });

});
