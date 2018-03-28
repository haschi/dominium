import { NO_ERRORS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs/observable/of';
import { BilanzComponent } from '../bilanz/bilanz.component';
import { Inventar } from '../inventar';

import { InventurService } from '../inventur.service';

import { InventarComponent } from './inventar.component';
import { AppMaterialModule } from '../../shared/app-material-module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule, By } from '@angular/platform-browser';
import { AppCovalentModuleModule } from '../../shared/app-covalent-module.module';
import { PositionComponent } from './position/position.component';
import { ZeileComponent } from './zeile/zeile.component';

describe('InventarComponent', () => {

    class Page {
        constructor(public fixture: ComponentFixture<InventarComponent>){
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
        anlagevermoegen:[{
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
        reinvermoegen : {
            summeDesVermoegens: "15.000,00 EUR",
            summeDerSchulden: "1.000,00 EUR",
            reinvermoegen: "14.000,00 EUR"
        }};

    const mock = {
        leseInventar: jasmine.createSpy('leseInventar'),
        inventar$: of(vollesInventar)
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
                {provide: Page, useFactory: Page.create },
                {provide: InventurService, useValue: mock}
            ],
            schemas: [NO_ERRORS_SCHEMA]
        })
            .compileComponents();
    }));

    fit('should create', inject([Page], (page: Page) => {
        expect(page.fixture).toBeTruthy();
    }));

    fit('sollte zur Eröffnungsbilanz weiterleiten', async(inject([Page], (page: Page) => {
        page.navigieren();
    })))
});
