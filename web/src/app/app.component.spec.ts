import { TestBed, inject, async } from '@angular/core/testing';
// Load the implementations that should be tested
import { AppComponent } from './app.component';
import { By } from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';
import { ResponseOptions, Response } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { AppState, KonfigurationState } from './reducer';
import { NgRedux } from '@angular-redux/store';
import { DebugElement } from '@angular/core';
import { HttpTestModule } from './httptest.module';
import { ReduxTestModule } from './reduxtest.module';
import { Router } from '@angular/router';
import { NoContentComponent } from './no-content/no-content.component';
import { Location } from '@angular/common';

import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { LeererInhaltComponent } from './leerer-inhalt/leerer-inhalt.component';
import { ReactiveFormsModule } from '@angular/forms';
import { JobService } from './shared/job.service';
import { AppRouterTestingModule } from './app-routing.module';

// TODO Fix this
describe('App', () => {

    let router, location;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [
                AppComponent,
                NoContentComponent,
                HomeComponent,
                AboutComponent,
                LeererInhaltComponent
            ],
            imports: [
                AppRouterTestingModule,
                ReduxTestModule,
                HttpTestModule,
                ReactiveFormsModule
            ],
            providers: [
                JobService,
            ]
        });
    });

    it('sollte die Buildnummer des Backend ermitteln',
        async(inject([MockBackend], (backend: MockBackend) => {
            backend.connections.subscribe((connection: MockConnection) => {
                connection.mockRespond(new Response(new ResponseOptions({
                    body: JSON.stringify({version: 123456})
                })));
            });

            const fixture = TestBed.createComponent(AppComponent);
            fixture.detectChanges();

            expect(fixture.debugElement.query(By.css('#api')).nativeElement.textContent)
                .toBe('API 123456');
        })));

    it('sollte die Konfiguration bereitstellen',
        async(inject([NgRedux, MockBackend], (store: NgRedux<AppState>, backend: MockBackend) => {

            const testkonfiguration: KonfigurationState = {
                name: 'service',
                version: 123456,
                _links: []
            };

            backend.connections.subscribe((connection: MockConnection) => {
                connection.mockRespond(new Response(new ResponseOptions({
                    body: JSON.stringify(testkonfiguration)
                })));
            });

            const fixture = TestBed.createComponent(AppComponent);
            fixture.detectChanges();

            expect(store.getState().konfiguration).toEqual(testkonfiguration);
        })));

    describe('falls offline', () => {
        beforeEach(inject([MockBackend], (backend: MockBackend) => {
            backend.connections.subscribe((connection: MockConnection) => {
                connection.mockError(new Error('Service unavailable'));
            });
        }));

        beforeEach(inject([Router, Location], (_router: Router, _location: Location) => {
            location = _location;
            router = _router;
        }));

        xit('sollte zur Offline Komponente weiterleiten', async(() => {
            const fixture = TestBed.createComponent(AppComponent);
            fixture.detectChanges();

            fixture.whenStable().then(() => {
                // Route wird nach ** gemappt.
                // Besserer Test: Prüfen, ob Offline Komponente angezeigt wird.
                expect(location.path()).toBe('/offline');
            });
        }));

        xit('sollte die Offline Komponente anzeigen', async(() => {
            const fixture = TestBed.createComponent(AppComponent);
            fixture.detectChanges();
            fixture.whenStable().then(() => {
                const errorMessage: HTMLElement = fixture.debugElement
                    .query(By.css('.card .card-content p')).nativeElement;

                const leererInhalt = fixture.debugElement.query(function (c) {
                    return c.name === 'leerer-inhalt';
                });
                expect(leererInhalt).not.toBeNull();
                expect(errorMessage.innerText).toEqual('Die Anwendung kann nicht geladen werden.');
                expect(errorMessage.hidden).toBeFalsy();
            });
        }));
    });
    /** Button events to pass to `DebugElement.triggerEventHandler` for RouterLink event handler */
    const buttonClickEvents = {
        left: {button: 0},
        right: {button: 2}
    };

    /** Simulate element click. Defaults to mouse left-button click event. */
    function click(el: DebugElement | HTMLElement, eventObj: any = buttonClickEvents.left): void {
        if (el instanceof HTMLElement) {
            el.click();
        } else {
            el.triggerEventHandler('click', eventObj);
        }
    }

    it('sollte das Seitenmenü initialisieren', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        fixture.detectChanges();

        const sideMenuButton = fixture.debugElement.query(By.css('[materialize="sideNav"]'));
        click(sideMenuButton.nativeElement);
    }));
});
