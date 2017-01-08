import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {NgRedux} from "ng2-redux";
import {AKTION} from "./reducer";

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
                console.info("ERROR!!!!!");
            }
        )
    }
}
