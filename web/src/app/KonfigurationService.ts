import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";

@Injectable()
export class KonfigurationService {
    constructor(private http: Http) {
    };

    laden(): void {
        console.info("KonfigurationServer.laden: GET http://localhost:8080/api")
        this.http.get("http://localhost:8080/api").subscribe((r: Response) => {
                console.log(r.json());
            },
            (error: any) => {
                console.log(error)
            })
    }
}
