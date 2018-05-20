///<reference path="../../../../node_modules/@angular/core/src/metadata/lifecycle_hooks.d.ts"/>
import {
    ChangeDetectionStrategy, Component, Input, Optional, TemplateRef
} from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import { Gruppe, Kategorie } from '../shared/gruppen.redux';

@Component({
    selector: 'app-inventur-gruppe',
    changeDetection: ChangeDetectionStrategy.Default,
    templateUrl: './gruppe.component.html',
    styleUrls: ['./gruppe.component.scss'],
})
export class GruppeComponent {

    @Input()
    public positionen: FormArray;

    @Input()
    public titel: TemplateRef<any>;

    @Input()
    public gruppe: Gruppe;

    @Input()
    public kategorie: Kategorie;

    hinzufuegen() {
        const waehrungsbetrag = new FormGroup({
            betrag: new FormControl('', Validators.required),
            waehrung: new FormControl('EUR', Validators.required)
        });

        const group = new FormGroup({
            kategorie: new FormControl('', Validators.required),
            position: new FormControl('', Validators.required),
            waehrungsbetrag: waehrungsbetrag,
        });

        this.positionen.push(group);
    }

    loeschen(index: number) {
        this.positionen.removeAt(index);
    }
}
