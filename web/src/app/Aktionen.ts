import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { NgRedux } from '@angular-redux/store';
import { AKTION } from './reducer';
import { jobGestartet } from './shared/jobs.actions';

@Injectable()
export class Aktionen {

    constructor(private http: Http, private store: NgRedux<any>) {
    };

    konfigurationLaden() {
        // TODO: URI sollte / sein. Proxy muss konfiguriert werden.
        this.http.get('http://localhost:8080/api').subscribe(
            (r: Response) => {
                this.store.dispatch({type: AKTION.LADEN, payload: r.json()});
            },
            () => {
                this.store.dispatch({type: AKTION.OFFLINE_GEHEN, payload: {verbunden: false}});
                this.store.dispatch({
                    type: AKTION.FEHLER, payload: {
                        nachricht: 'Anmeldung nicht verfügbar.',
                        route: ['leerer-inhalt'],
                        kompensation: [{
                            titel: 'Noch einmal versuchen',
                            aktion: () => this.konfigurationLaden()}]
                    }
                });
            }
        );
    }

    legeHaushaltsbuchAn(name: String) {
        this.http.post('http://localhost:8080/api/hauptbuch/haushaltsbuchanlage', null)
            .subscribe((r: Response) => {
            if (r.status === 202) {
                this.store.dispatch(jobGestartet(r.headers.get('Location')));
            }

            if (r.status === 200) {
                this.store.dispatch({
                    type: AKTION.HAUSHALTSBUCH_ERSTELLT,
                    payload: r.json()
                });
            }
        }, (err) => {
            this.store.dispatch({
                type: AKTION.FEHLER, payload: {
                    nachricht: 'Keine Verbindung',
                    route: [],
                    kompensation: [{
                        titel: 'Wiederholen', aktion: () => {
                            this.legeHaushaltsbuchAn(name);
                        }
                    }]
                }
            });
            console.log('FEHLER: ' + err);
        });
    }
}
