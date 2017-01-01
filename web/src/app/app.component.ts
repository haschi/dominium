import {Component, ViewEncapsulation, AfterViewInit, ElementRef} from "@angular/core";
import {Http, Response} from "@angular/http";
// TODO: weder jquery noch materialize sollten explizit eingebunden werden
// Beide Bibliotheken sollen global eingebunden werden, um zu verhindern,
// dass sie in jeder Komponente dupliziert werden. Das muss in webpack
// entsprechend konfiguriert werden, so dass es auch im Test funktioniert.
import * as jQuery from "jquery";
import "materialize-css/dist/js/materialize.js";
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
    templateUrl: 'app.component.html'
})
export class AppComponent implements AfterViewInit {

    url = 'https://twitter.com/AngularClass';
    index = 'Kein Serveranwort erhalten';
    build = '';

    constructor(private elementRef: ElementRef, private http: Http) {
    }

    ngOnInit() {
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

    ngAfterViewInit(): void {
        console.log("ngAfterViewInit()");
        let native = jQuery(this.elementRef.nativeElement);
        let button = native.find(".button-collapse");
        button.sideNav();
    }
}
