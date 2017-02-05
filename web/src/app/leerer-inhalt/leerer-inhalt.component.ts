import { Component } from '@angular/core';
import {VerbindungState, AppState} from "../reducer";
import {Observable} from "rxjs";
import {select} from "@angular-redux/store";

@Component({
    selector: 'leerer-inhalt',
    templateUrl: 'leerer-inhalt.component.html'
})
export class LeererInhaltComponent {
    @select((s: AppState) => s.verbindung)
    fehler$: Observable<VerbindungState>;

    kompensieren(eintrag: number) {
        console.info("Kompensation ausgewählt: " + eintrag);
        this.fehler$.do((f) => {console.info("Kompensation: ")});
    }
}
