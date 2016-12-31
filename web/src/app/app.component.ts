import {Component, ViewEncapsulation} from "@angular/core";
import {Http, Response} from "@angular/http";

/*
 * App Component
 * Top Level Component
 */
@Component({
    selector: 'app',
    encapsulation: ViewEncapsulation.None,
    styleUrls: [
        './app.component.css'
    ],
    templateUrl: './app.component.html'
})
export class AppComponent {
    // angularclassLogo = 'assets/img/angularclass-avatar.png';
    // name = 'Angular 2 Webpack Starter';
    url = 'https://twitter.com/AngularClass';
    index = 'Kein Serveranwort erhalten';
    build = '';

    constructor(private http: Http) {

    }

    ngOnInit() {
        // jQuery(".button-collapse").sideNav();
        // Das ist nur ein Versuch: GET Request zum Backend, um die
        // Einstiefspunkte zu finden.
        console.log('app.component:ngOnInit()');
        this.http.get("http://localhost:8080/api").subscribe((r: Response) => {
                console.log(r.json());
                this.index = r.text();
                this.build = r.json().build;
            },
            (error: any) => {
                console.log(error)
            })
    }
}

/*
 * Please review the https://github.com/AngularClass/angular2-examples/ repo for
 * more angular app examples that you may copy/paste
 * (The examples may not be updated as quickly. Please open an issue on github for us to update it)
 * For help or questions please contact us at @AngularClass on twitter
 * or our chat on Slack at https://AngularClass.com/slack-join
 */
