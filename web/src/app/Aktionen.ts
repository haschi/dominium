import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {NgRedux} from "ng2-redux";
import {AKTION} from "./reducer";

@Injectable()
export class Aktionen {

    constructor(private http: Http, private store: NgRedux<any>) {
    };

    konfigurationLaden() {
        console.info("Kommando konfigurationLaden");
        this.http.get('http://localhost:8080/api').subscribe(
            (r: Response) => {
                console.info("Response: " + r.text());
                this.store.dispatch({type: AKTION.LADEN, payload: r.json()})
            },
            (error) => {
                console.info("ERROR!!!!!");
            }
        )
    }
}
