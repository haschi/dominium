import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {NgRedux} from "ng2-redux";
import {AKTION} from "./reducer";
import {Router} from "@angular/router";

@Injectable()
export class Aktionen {

    constructor(private http: Http, private store: NgRedux<any>) {
    };

    konfigurationLaden() {
        // TODO: URI sollte / sein. Proxy muss konfiguriert werden.
        this.http.get('http://localhost:8080/api').subscribe(
            (r: Response) => {
                this.store.dispatch({type: AKTION.LADEN, payload: r.json()})
            },
            (error) => {
                this.store.dispatch({type: AKTION.OFFLINE_GEHEN, payload: {verbunden: false}})
                console.info("ERROR!!!!!");
            }
        )
    }
}
