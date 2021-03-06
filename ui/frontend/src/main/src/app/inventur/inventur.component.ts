import {
    ChangeDetectionStrategy, Component, HostListener, OnInit, QueryList,
    ViewChildren
} from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { LoggerService } from '../shared/logger.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';

import { Observable } from 'rxjs/Observable';
import { Vermoegenswert } from './bilanz/bilanz.model';
import { GruppenState } from './shared/gruppen.redux';
import { Inventar, InventurEingabe } from './shared/inventar';
import { ActivatedRoute, Router } from '@angular/router';
import { InventurService } from './inventur.service';
import { select } from '@angular-redux/store';
import { Inventarposition } from './shared/inventarposition';
import { StepperSelectionEvent } from '@angular/cdk/stepper';
import { GruppeComponent } from './gruppe/gruppe.component';

@Component({
    selector: 'app-inventur',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './inventur.component.html',
    styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit {

    @ViewChildren(GruppeComponent)
    private gruppen: QueryList<GruppeComponent>;

    private aktuellerStep: number = 0;

    inventur: FormGroup;

    private model: any;

    @select(['inventur', 'inventar'])
    private model$: Observable<Inventar>;

    private eingabe$: Observable<InventurEingabe>;

    inventurId$: Observable<string>;

    inventurGruppen$: Observable<GruppenState>;

    constructor(private builder: FormBuilder,
                private log: LoggerService,
                private router: Router,
                private active: ActivatedRoute,
                private inventurService: InventurService) {

        this.inventurId$ = inventurService.inventurid$;
    }

    ngOnInit() {
        this.inventurService.leseInventurGruppen();

        this.inventur = this.builder.group({
            anlagevermoegen: this.builder.array([]),
            umlaufvermoegen: this.builder.array([]),
            schulden: this.builder.array([])
        });

        this.eingabe$ = this.inventur.valueChanges
            .map(formulareingabe => this.formulareingabeKonvertieren(formulareingabe))

        this.inventurGruppen$ = this.inventurService.gruppen$
    }

    auswahlGeaendert(event: StepperSelectionEvent) {
        this.aktuellerStep = event.selectedIndex
    }

    inventarpositionKonvertieren(poisition: any): Inventarposition {
        return {
            kategorie: poisition.kategorie,
            position: poisition.position,
            waehrungsbetrag: `${poisition.waehrungsbetrag.betrag} ${poisition.waehrungsbetrag.waehrung}`
        }
    }

    speichern() {
        this.model = this.inventur.value;
        let inventar = this.formulareingabeKonvertieren(this.inventur.value);

        let id = this.active.snapshot.params.id;
        this.inventurService.erfasseInventar(inventar);

        this.router.navigate(['inventar', id]);
    }

    hinzufuegen() {
        let komponente = this.gruppen.toArray()[this.aktuellerStep];
        komponente.hinzufuegen();
    }

    @HostListener('document:keypress', ['$event'])
    tastatureingabe($event: KeyboardEvent): boolean {
        if ($event.key === '+') {
            if (this.inventur.valid) {
                this.hinzufuegen();
            }

            return false;
        }

        return true
    }

    private formulareingabeKonvertieren(value): InventurEingabe {
        return {
            anlagevermoegen: value.anlagevermoegen.map(this.inventarpositionKonvertieren),
            umlaufvermoegen: value.umlaufvermoegen.map(this.inventarpositionKonvertieren),
            schulden: value.schulden.map(this.inventarpositionKonvertieren),
        };
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
