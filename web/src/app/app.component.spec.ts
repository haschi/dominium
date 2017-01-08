import {TestBed, inject, async} from "@angular/core/testing";
// Load the implementations that should be tested
import {AppComponent} from "./app.component";
import {By} from "@angular/platform-browser";
import {RouterTestingModule} from "@angular/router/testing";
import {BaseRequestOptions, Http, ResponseOptions, Response} from "@angular/http";
import {MockBackend, MockConnection} from "@angular/http/testing";
import {Aktionen} from "./Aktionen";
import {rootReducer, INIT_STATE, AppState} from "./reducer";
import {NgRedux, NgReduxModule} from "ng2-redux";
import {NgZone, DebugElement} from "@angular/core";
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

    /** Button events to pass to `DebugElement.triggerEventHandler` for RouterLink event handler */
    const ButtonClickEvents = {
        left:  { button: 0 },
        right: { button: 2 }
    };

    /** Simulate element click. Defaults to mouse left-button click event. */
    function click(el: DebugElement | HTMLElement, eventObj: any = ButtonClickEvents.left): void {
        if (el instanceof HTMLElement) {
            el.click();
        } else {
            el.triggerEventHandler('click', eventObj);
        }
    }

    it('sollte das Seitenmenü initialisieren', () => {
        let fixture = TestBed.createComponent(AppComponent);
        fixture.detectChanges();

        let sideMenuElement: HTMLElement = fixture.debugElement.query(By.css("#side-menu")).nativeElement;
        // const spy = spyOn(sideMenuElement, "scrollTo"); // scrollBy scrollTo;

        let sideMenuButton = fixture.debugElement.query(By.css("[materialize=\"sideNav\"]"));
        click(sideMenuButton.nativeElement);

        fixture.whenStable().then(() => {
            console.info("Stable!");
        });

        // expect(spy.calls.any()).toBeTruthy();
        // let sideMenuElement = fixture.debugElement.query(By.css("#side-menu")).nativeElement;


        // expect(sideMenuElement.hidden).toBe(false);
    });
});
