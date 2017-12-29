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
import { Inventarposition } from './inventarposition';

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

        this.inventar$ = this.inventur.valueChanges
            .map(formulareingabe => this.formulareingabeKonvertieren(formulareingabe))
    }

    inventarpositionKonvertieren(poisition: any): Inventarposition {
        return {
            position: poisition.position,
            waehrungsbetrag: `${poisition.waehrungsbetrag.betrag} ${poisition.waehrungsbetrag.waehrung}`
        }
    }

    speichern() {
        this.model = this.inventur.value;
        this.log.log('Inventar erfassen');
        this.log.log(JSON.stringify(this.inventur.value));

        console.info("speichern");
        let inventar = this.formulareingabeKonvertieren(this.inventur.value);

        this.inventurService.erfasseInventar(inventar);
    }

    private formulareingabeKonvertieren(value) {
        let inventar: Inventar = {
            anlagevermoegen: value.anlagevermoegen.map(this.inventarpositionKonvertieren),
            umlaufvermoegen: value.umlaufvermoegen.map(this.inventarpositionKonvertieren),
            schulden: value.schulden.map(this.inventarpositionKonvertieren)
        };
        return inventar;
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
