import { Component, OnInit } from '@angular/core';
import { Inventar } from '../inventar';
import { Observable } from 'rxjs/Observable';

import { InventurService } from '../inventur.service';

@Component({
    selector: 'app-inventar',
    templateUrl: './inventar.component.html',
    styleUrls: ['./inventar.component.scss']
})
export class InventarComponent implements OnInit {

    inventar$: Observable<Inventar>;

    constructor(private inventur: InventurService) {
        this.inventar$ = this.inventur.inventar$
    }

    ngOnInit() {
        this.inventur.leseInventar();
    }

    haushaltsbuchAnlegen() {
    }

    inventurWiederholen() {
        this.inventur.beginnen();
    }
}
