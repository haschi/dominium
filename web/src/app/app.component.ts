import {Component, ViewEncapsulation, AfterViewInit, ElementRef} from "@angular/core";
import "materialize-css/dist/js/materialize.js";
import {Aktionen} from "./Aktionen";
import {AppState, KonfigurationState} from "./reducer";
import {select} from "ng2-redux";
import {Observable} from "rxjs";

@Component({
    selector: 'app',
    encapsulation: ViewEncapsulation.None,
    styleUrls: ['./app.component.css'],
    templateUrl: 'app.component.html'
})
export class AppComponent {

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
}
