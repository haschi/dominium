import { Component, OnInit } from '@angular/core';
import { InventurState } from '../shared/inventur.redux';
import { Inventar } from '../shared/inventar';
import { Observable } from 'rxjs/Observable';

import { InventurService } from '../inventur.service';

@Component({
    selector: 'app-inventar',
    templateUrl: './inventar.component.html',
    styleUrls: ['./inventar.component.scss']
})
export class InventarComponent implements OnInit {

    inventar$: Observable<Inventar>;
    inventurId$: Observable<string>;
    inventurState$: Observable<InventurState>

    constructor(private inventur: InventurService) {
        this.inventar$ = this.inventur.inventar$;
        this.inventurId$ = this.inventur.inventurid$
        this.inventurState$ = this.inventur.state$
    }

    ngOnInit() {
        this.inventur.leseInventar();
    }

    inventurWiederholen() {
        this.inventur.beginnen();
    }

    inventurBeenden(id: string) {
        this.inventur.beenden(id)
    }
}
