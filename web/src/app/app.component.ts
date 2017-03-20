import { Component, ViewEncapsulation } from '@angular/core';
import { Aktionen } from './Aktionen';
import { AppState, KonfigurationState, VerbindungState } from './reducer';
import { Router } from '@angular/router';
import { select } from '@angular-redux/store';
import { JobService } from './shared/job.service';
import { Observable } from 'rxjs/Observable';
@Component({
    selector: 'app-dominium',
    encapsulation: ViewEncapsulation.None,
    styleUrls: ['./app.component.css'],
    templateUrl: 'app.component.html',
})
export class AppComponent implements OnInit {

    url = 'https://twitter.com/AngularClass';
    index = 'Kein Serveranwort erhalten';

    @select((s: AppState) => s.konfiguration.version)
    api$: Observable<KonfigurationState>;

    @select((s: AppState) => s.verbindung)
    fehler$: Observable<VerbindungState>;

    constructor(private aktionen: Aktionen, private router: Router, private job: JobService) {
    }

    ngOnInit() {
        this.job.init();
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
