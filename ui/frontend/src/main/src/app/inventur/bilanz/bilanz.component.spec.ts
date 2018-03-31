import { Component, DebugElement } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { AppMaterialModule } from '../../shared/app-material-module';
import { PositionComponent } from '../inventar/position/position.component';
import { ZeileComponent } from '../inventar/zeile/zeile.component';
import { Inventarposition } from '../inventarposition';
import { BilanzServiceService } from './bilanz-service.service';

import { BilanzComponent } from './bilanz.component';
import { Eroeffnungsbilanz } from './bilanz.model';

@Component({
    selector: 'app-query-error',
    template: ''
})
class MockQueryErrorComponent {
}

describe('BilanzComponent', () => {
    let mock = {
        bilanz$: new BehaviorSubject<Eroeffnungsbilanz>(null)
    };

    class Page {
        constructor(public fixture: ComponentFixture<BilanzComponent>) {
        }

        static create(): Page {
            const page = new Page(TestBed.createComponent(BilanzComponent));
            page.fixture.detectChanges();

            return page;
        }

        get ladefortschritt(): DebugElement {
            return this.fixture.debugElement.query(By.directive(MockQueryErrorComponent));
        }

        get bilanzsummen(): string[] {
            return this.fixture.debugElement.query(By.css('#summen'))
                .queryAll(By.directive(ZeileComponent))
                .map(c => c.componentInstance as ZeileComponent)
                .map(i => i.betrag)
        }

        get anlagevermögen(): Inventarposition[] {
            return this.fixture.debugElement.query(By.css('#anlagevermoegen'))
                .queryAll(By.directive(PositionComponent))
                .map(c => c.componentInstance as PositionComponent)
                .map(i => i.position)
        }

        get umlaufvermögen(): Inventarposition[] {
            return this.fixture.debugElement.query(By.css('#umlaufvermoegen'))
                .queryAll(By.directive(PositionComponent))
                .map(c => c.componentInstance as PositionComponent)
                .map(i => i.position)
        }

        get eigenkapital(): Inventarposition[] {
            return this.fixture.debugElement.query(By.css('#eigenkapital'))
                .queryAll(By.directive(PositionComponent))
                .map(c => c.componentInstance as PositionComponent)
                .map(i => i.position)
        }

        get fremdkapital(): Inventarposition[] {
            return this.fixture.debugElement.query(By.css('#fremdkapital'))
                .queryAll(By.directive(PositionComponent))
                .map(c => c.componentInstance as PositionComponent)
                .map(i => i.position)
        }

        showHtml() {
            console.info(this.fixture.nativeElement.innerHTML);
        }
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [BilanzComponent, MockQueryErrorComponent, PositionComponent, ZeileComponent],
            imports: [AppMaterialModule],
            providers: [
                {provide: Page, useFactory: Page.create},
                {provide: BilanzServiceService, useValue: mock}
            ]
        })
            .compileComponents();
    }));

    it('should create', inject([Page], (page: Page) => {
        expect(page.fixture).toBeTruthy();
    }));

    describe('ohne geladene Eroeffnungsbilanz', () => {

        it('sollte Ladefortschritt anzeigen', async(inject([Page], (page: Page) => {
            page.fixture.detectChanges();
            expect(page.ladefortschritt).toBeTruthy()
        })));
    });

    describe('mit geladener Eroeffnungsbilanz', () => {
        beforeEach(() => {
            mock.bilanz$.next({
                aktiva: {
                    anlagevermoegen: [{position: 'Eigentumswohnung', waehrungsbetrag: '120.000,00 EUR'}],
                    umlaufvermoegen: [{position: 'Girokonto', waehrungsbetrag: '12.000,00 EUR'}]},
                passiva: {eigenkapital: [], fremdkapital: []},
                summe: "10.000,00 EUR"
            });
        });

        it('sollte Ladefortschritt nicht anzeigen', async(inject([Page], (page: Page) => {
            expect(page.ladefortschritt).toBeFalsy()
        })));

        it('sollte 2 Bilanzsummen anzeige', inject([Page], (page: Page) => {
            expect(page.bilanzsummen).toEqual(['10.000,00 EUR', '10.000,00 EUR'])
        }));

        it('sollte alle Anlagevermögen-Positionen anzeigen', inject([Page], (page: Page) => {
            mock.bilanz$.subscribe(bilanz => {
                expect(page.anlagevermögen).toEqual(bilanz.aktiva.anlagevermoegen)
            })
        }));

        it('sollte alle Umlaufvermoegen-Positionen anzeigen', inject([Page], (page: Page) => {
            mock.bilanz$.subscribe(bilanz => {
                expect(page.umlaufvermögen).toEqual(bilanz.aktiva.umlaufvermoegen)
            })
        }))

        it('sollte alle Eigenkapital-Positionen anzeigen', inject([Page], (page: Page) => {
            mock.bilanz$.subscribe(bilanz => {
                expect(page.eigenkapital).toEqual(bilanz.passiva.eigenkapital)
            })
        }))

        it('sollte alle Fremdkapital-Positionen anzeigen', inject([Page], (page: Page) => {
            mock.bilanz$.subscribe(bilanz => {
                expect(page.fremdkapital).toEqual(bilanz.passiva.fremdkapital)
            })
        }))
    })
});
