import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { LoggerService } from '../shared/logger.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';

import { Observable } from 'rxjs/Observable';
import { Inventar } from './inventar';
import { Router } from '@angular/router';
import { InventurService } from './inventur.service';
import { select } from '@angular-redux/store';

@Component({
    selector: 'app-inventur',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './inventur.component.html',
    styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit {

    private inventur: FormGroup;

    private model: any;

    @select(['inventur', 'inventar'])
    private model$: Observable<Inventar>;

    private inventar$: Observable<Inventar>;

    inventurId$: Observable<string>;

    constructor(private builder: FormBuilder,
                private log: LoggerService,
                private router: Router,
                private inventurService: InventurService) {
        this.inventurId$ = inventurService.inventurid$;
    }

    ngOnInit() {
        this.inventur = this.builder.group({
            anlagevermoegen: this.builder.array([]),
            umlaufvermoegen: this.builder.array([]),
            schulden: this.builder.array([])
        });
    }

    speichern() {
        this.model = this.inventur.value;
        this.log.log('Inventur beginnen');
        this.log.log(JSON.stringify(this.inventur.value));

        console.info("speichern");

        let x = this.inventur.value.anlagevermoegen.map(e => {
            console.info("Convert " + JSON.stringify(e));
            return ({
                position: e.position,
                währungsbetrag: `${e.währungsbetrag.betrag} ${e.währungsbetrag.währung}`
            })
        });

        console.info("Anlageverägen: " + JSON.stringify(this.inventur.value.anlagevermoegen));
        console.info("MAP: " + JSON.stringify(x));

        let inventar: Inventar = {
            anlagevermoegen: x,
            umlaufvermoegen: [],
            schulden: []
        };

        this.inventurService.erfasseInventar(inventar);
    }

    get anlagevermoegen(): AbstractControl {
        return this.inventur.get('anlagevermoegen');
    }

    get umlaufvermoegen(): AbstractControl {
        return this.inventur.get('umlaufvermoegen');
    }

    get schulden(): AbstractControl {
        return this.inventur.get('schulden');
    }
}
