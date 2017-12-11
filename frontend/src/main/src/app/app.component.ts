import { AfterViewInit, Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {

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

    constructor(private _iconRegistry: MatIconRegistry,
                private _domSanitizer: DomSanitizer) {

        const s = 'https://raw.githubusercontent.com/Teradata/covalent-quickstart/develop/src/assets/icons';

        this._iconRegistry.addSvgIconInNamespace('assets', 'teradata-ux',
            this._domSanitizer.bypassSecurityTrustResourceUrl(s + '/teradata-ux.svg'));

        this._iconRegistry.addSvgIconInNamespace('assets', 'covalent',
            this._domSanitizer.bypassSecurityTrustResourceUrl(s + '/covalent.svg'));

        this._iconRegistry.addSvgIconInNamespace('assets', 'covalent-mark',
            this._domSanitizer.bypassSecurityTrustResourceUrl(s + '/covalent-mark.svg'));

    }

    ngAfterViewInit(): void {

    }
}
