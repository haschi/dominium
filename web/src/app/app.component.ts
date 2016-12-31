import {Component, ViewEncapsulation, AfterViewInit, ElementRef} from "@angular/core";
import {Http, Response} from "@angular/http";
import * as jQuery from "jquery";
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
export class AppComponent implements AfterViewInit {

    // angularclassLogo = 'assets/img/angularclass-avatar.png';
    // name = 'Angular 2 Webpack Starter';
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
        jQuery(this.elementRef.nativeElement).find(".button-collapse").sideNav();
        // jQuery(this.elementRef.nativeElement).find(".button-collapse").click(() => {
        //     alert("geklickt!");
        // })
    }
}
