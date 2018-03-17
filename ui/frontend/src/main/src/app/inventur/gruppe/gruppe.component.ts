///<reference path="../../../../node_modules/@angular/core/src/metadata/lifecycle_hooks.d.ts"/>
import {
    ChangeDetectionStrategy, Component, Input, OnChanges, SimpleChanges,
    TemplateRef
} from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-inventur-gruppe',
    changeDetection: ChangeDetectionStrategy.Default,
    templateUrl: './gruppe.component.html',
    styleUrls: ['./gruppe.component.scss'],
})
export class GruppeComponent implements OnChanges {
    ngOnChanges(changes: SimpleChanges): void {
        console.log("changed")
    }

    @Input()
    public positionen: FormArray;

    @Input()
    public titel: TemplateRef<any>;

    hinzufuegen() {
        console.log("Hinzufügen")
        const waehrungsbetrag = new FormGroup({
            betrag: new FormControl('', Validators.required),
            waehrung: new FormControl('EUR', Validators.required)
        });

        const group = new FormGroup({
            position: new FormControl('', Validators.required),
            waehrungsbetrag: waehrungsbetrag,
        });

        this.positionen.push(group);

        console.log(`Positionen: ${this.positionen.length}`)
    }

    loeschen(index: number) {
        this.positionen.removeAt(index);
    }
}
