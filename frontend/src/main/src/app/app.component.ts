import { AfterViewInit, Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit, OnInit {

    routes: Object[] = [
        {
            title: 'Home',
            route: '',
            icon: 'home',
        }, {
            title: 'Technology',
            route: '/',
            icon: 'laptop_mac',
        }, {
            title: 'Locations',
            route: '/',
            icon: 'language',
        }, {
            title: 'Job Openings',
            route: '/',
            icon: 'assignment',
        }, {
            title: 'Leadership',
            route: '/',
            icon: 'people',
        },
    ];
    private version$: Observable<Object>;

    constructor(private http: HttpClient,
                private _iconRegistry: MatIconRegistry,
                private _domSanitizer: DomSanitizer) {

        const s = 'https://raw.githubusercontent.com/Teradata/covalent-quickstart/develop/src/assets/icons';

        this._iconRegistry.addSvgIconInNamespace('assets', 'teradata-ux',
            this._domSanitizer.bypassSecurityTrustResourceUrl(s + '/teradata-ux.svg'));

        this._iconRegistry.addSvgIconInNamespace('assets', 'covalent',
            this._domSanitizer.bypassSecurityTrustResourceUrl(s + '/covalent.svg'));

        this._iconRegistry.addSvgIconInNamespace('assets', 'covalent-mark',
            this._domSanitizer.bypassSecurityTrustResourceUrl(s + '/covalent-mark.svg'));

    }

    ngOnInit(): void {
        console.info("NG onInit")
        this.version$ = this.http.get<String>("/version")
    }

    ngAfterViewInit(): void {
        console.info("After Init")

    }
}
