import { Component, DebugElement } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { AppMaterialModule } from '../../shared/app-material-module';
import { PositionComponent } from '../inventar/position/position.component';
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
            const element: DebugElement[] = this.fixture.debugElement.queryAll(By.css('.bilanzsumme span'));
            return element.map( e => e.nativeElement.innerHTML);
        }

        showHtml() {
            console.info(this.fixture.nativeElement.innerHTML);
        }
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [BilanzComponent, MockQueryErrorComponent, PositionComponent],
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
                aktiva: {anlagevermoegen: [], umlaufvermoegen: []},
                passiva: {eigenkapital: [], fremdkapital: []},
                summe: "10.000,00 EUR"
            });
        });

        it('sollte Ladefortschritt nicht anzeigen', async(inject([Page], (page: Page) => {
            expect(page.ladefortschritt).toBeFalsy()
        })));

        it('sollte 2 Bilanzsummen anzeige', inject([Page], (page: Page) => {
            expect(page.bilanzsummen).toEqual(['10.000,00 EUR', '10.000,00 EUR'])
        }))
    })
});
