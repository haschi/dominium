import { Component, DebugElement } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Subject } from 'rxjs/Subject';
import { AppMaterialModule } from '../../shared/app-material-module';
import { PositionComponent } from '../inventar/position/position.component';
import { ZeileComponent } from '../inventar/zeile/zeile.component';
import { Inventarposition } from '../inventarposition';
import { BilanzServiceService } from './bilanz-service.service';

import { BilanzComponent } from './bilanz.component';
import { Eroeffnungsbilanz } from './bilanz.model';
import Spy = jasmine.Spy;

@Component({
    selector: 'app-query-error',
    template: ''
})
class MockQueryErrorComponent {
}

describe('BilanzComponent', () => {

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

        get fehlbetrag(): Inventarposition[] {
            let element = this.fixture.debugElement.query(By.css('#fehlbetrag'))

            if (element == null) {
                return []
            }

            return element.queryAll(By.directive(PositionComponent))
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
                {
                    provide: BilanzServiceService, useFactory: () => {
                        return {
                            bilanz$: new BehaviorSubject<Eroeffnungsbilanz>(null),
                            ladeBilanz: jasmine.createSpy('ladeBilanz')
                        }
                    }
                }
            ]
        })
            .compileComponents();
    }));

    it('should create', inject([Page], (page: Page) => {
        expect(page.fixture).toBeTruthy();
    }));

    it('sollte Eröffnungsbilanz lesen',
        inject([Page, BilanzServiceService], (page: Page, service: BilanzServiceService) => {
            let spy = service.ladeBilanz as Spy

            expect(spy).toHaveBeenCalled()
        }))

    describe('ohne geladene Eröffnungsbilanz', () => {

        it('sollte Abfrage-Fehler anzeigen', async(inject([Page], (page: Page) => {
            page.fixture.detectChanges();
            expect(page.ladefortschritt).toBeTruthy()
        })));
    });

    [
        {
            beschreibung: "Eröffnungsbilanz ohne Fehlbetrag",
            eröffnungsbilanz: {
                aktiva: {
                    anlagevermoegen: [{
                        position: 'Eigentumswohnung',
                        waehrungsbetrag: '120.000,00 EUR'
                    }],
                    umlaufvermoegen: [{position: 'Girokonto', waehrungsbetrag: '12.000,00 EUR'}],
                    fehlbetrag: [],
                    summe: '10.000,00 EUR'
                },
                passiva: {
                    eigenkapital: [],
                    fremdkapital: [],
                    summe: "10.000,00 EUR"
                }
            }
        },
        {
            beschreibung: "Eröffnungsbilanz mit Fehlbetrag",
            eröffnungsbilanz: {
                aktiva: {
                    anlagevermoegen: [{
                        position: 'Eigentumswohnung',
                        waehrungsbetrag: '120.000,00 EUR'
                    }],
                    umlaufvermoegen: [{position: 'Girokonto', waehrungsbetrag: '12.000,00 EUR'}],
                    fehlbetrag: [{position: "Fehlbetrag", waehrungsbetrag: '1.000,00 EUR'}],
                    summe: '10.000,00 EUR'
                },
                passiva: {
                    eigenkapital: [],
                    fremdkapital: [],
                    summe: "10.000,00 EUR"
                }
            }
        }
    ].forEach(testfall => {
        describe(testfall.beschreibung, () => {
            beforeEach(inject([BilanzServiceService], (service: BilanzServiceService) => {
                let mock = service.bilanz$ as Subject<Eroeffnungsbilanz>
                mock.next(testfall.eröffnungsbilanz);
            }));

            it('sollte Abfrage-Fehler nicht anzeigen', inject([Page], (page: Page) => {
                expect(page.ladefortschritt).toBeFalsy()
            }));

            it('sollte zwei Bilanzsummen anzeige', inject([Page], (page: Page) => {
                expect(page.bilanzsummen).toEqual(['10.000,00 EUR', '10.000,00 EUR'])
            }));

            it('sollte alle Anlagevermögen-Positionen anzeigen', inject([Page], (page: Page) => {

                expect(page.anlagevermögen).toEqual(testfall.eröffnungsbilanz.aktiva.anlagevermoegen)
            }));

            it('sollte alle Umlaufvermögen-Positionen anzeigen', inject([Page], (page: Page) => {
                expect(page.umlaufvermögen).toEqual(testfall.eröffnungsbilanz.aktiva.umlaufvermoegen)
            }));

            it('sollte alle Fehlbeträge anzeigen', inject([Page], (page: Page) => {
                expect(page.fehlbetrag).toEqual(testfall.eröffnungsbilanz.aktiva.fehlbetrag)
            }))

            it('sollte alle Eigenkapital-Positionen anzeigen', inject([Page], (page: Page) => {
                expect(page.eigenkapital).toEqual(testfall.eröffnungsbilanz.passiva.eigenkapital)
            }));

            it('sollte alle Fremdkapital-Positionen anzeigen', inject([Page], (page: Page) => {
                expect(page.fremdkapital).toEqual(testfall.eröffnungsbilanz.passiva.fremdkapital)
            }))
        })
    })
});
