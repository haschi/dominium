import {Component, ViewEncapsulation, AfterViewInit, ElementRef} from "@angular/core";
import {Http, Response} from "@angular/http";
// TODO: weder jquery noch materialize sollten explizit eingebunden werden
// Beide Bibliotheken sollen global eingebunden werden, um zu verhindern,
// dass sie in jeder Komponente dupliziert werden. Das muss in webpack
// entsprechend konfiguriert werden, so dass es auch im Test funktioniert.
import * as jQuery from "jquery";
import "materialize-css/dist/js/materialize.js";
import {Aktionen} from "./Aktionen";
import {AppState, KonfigurationState} from "./reducer";
import {select} from "ng2-redux";
import {Observable} from "rxjs";
/*
 * App Component
 * Top Level Component
 */
@Component({
    selector: 'app',
    encapsulation: ViewEncapsulation.None,
    styleUrls: [
        './app.component.css'
    ],
    templateUrl: 'app.component.html'
})
export class AppComponent implements AfterViewInit {

    url = 'https://twitter.com/AngularClass';
    index = 'Kein Serveranwort erhalten';

    @select((s: AppState) => s.konfiguration.build)
    build$: Observable<KonfigurationState>;

    constructor(
        private aktionen: Aktionen,
        private elementRef: ElementRef)   {}

    ngOnInit() {
        this.aktionen.konfigurationLaden();
    }

    ngAfterViewInit(): void {
        console.log("ngAfterViewInit()");

        // let native = jQuery(this.elementRef.nativeElement);
        // let button = native.find(".button-collapse");
        // button.sideNav();
    }
}
