import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {NgRedux} from "ng2-redux";
import {AKTION} from "./reducer";

@Injectable()
export class Aktionen {

    constructor(private http: Http, private store: NgRedux<any>) {
    };

    konfigurationLaden() {
        this.http.get('/').subscribe(
            (r: Response) => {
                this.store.dispatch({type: AKTION.LADEN, payload: r.json()})
            },
            (error) => {
                console.info("ERROR!!!!!");
            }
        )
    }
}
