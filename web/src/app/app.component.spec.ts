import {TestBed, inject} from "@angular/core/testing";
// Load the implementations that should be tested
import {AppComponent} from "./app.component";
import {By} from "@angular/platform-browser";
import {RouterTestingModule} from "@angular/router/testing";
import {BaseRequestOptions, Http, ResponseOptions, Response} from "@angular/http";
import {MockBackend, MockConnection} from "@angular/http/testing";
import {Aktionen} from "./Aktionen";
import {rootReducer, INIT_STATE, AppState} from "./reducer";
import {NgRedux, NgReduxModule} from "ng2-redux";
import {NgZone} from "@angular/core";
import {HttpTestModule} from "./httptest.module";
import {ReduxTestModule} from "./reduxtest.module";

describe('App', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [AppComponent],
            imports: [RouterTestingModule, ReduxTestModule, HttpTestModule],
        });
    });

    it('sollte die Buildnummer des Backend ermitteln',
        inject([MockBackend], (backend: MockBackend) => {
            backend.connections.subscribe((connection: MockConnection) => {
                connection.mockRespond(new Response(new ResponseOptions({
                    body: JSON.stringify({build: 123456})
                })))
            });

            let fixture = TestBed.createComponent(AppComponent);
            fixture.detectChanges();

            expect(fixture.debugElement.query(By.css("#build")).nativeElement.textContent).toBe("Build 123456");
    }));

    xit('sollte das Seitenmenü initialisieren', () => {
        let fixture = TestBed.createComponent(AppComponent);
        fixture.detectChanges();

        expect(fixture.debugElement.query(By.css("#side-menu")).nativeElement).toBeDefined();
    })
});
