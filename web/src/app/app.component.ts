import { Component, ViewEncapsulation } from '@angular/core';
import 'materialize-css/dist/js/materialize.js';
import { Aktionen } from './Aktionen';
import { AppState, KonfigurationState, VerbindungState } from './reducer';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { select } from '@angular-redux/store';

@Component({
    selector: 'app',
    encapsulation: ViewEncapsulation.None,
    styleUrls: ['./app.component.css'],
    templateUrl: 'app.component.html',
})
export class AppComponent {

    url = 'https://twitter.com/AngularClass';
    index = 'Kein Serveranwort erhalten';

    @select((s: AppState) => s.konfiguration.version)
    api$: Observable<KonfigurationState>;

    @select((s: AppState) => s.verbindung)
    fehler$: Observable<VerbindungState>;

    constructor(private aktionen: Aktionen, private router: Router) {
    }

    ngOnInit() {
        this.aktionen.konfigurationLaden();

        this.fehler$.subscribe(v => {
            if (v.route != null) {
                if (v.route === []) {
                } else {
                    this.router.navigate([v.route]);
                }
            }
        });
    }
}
