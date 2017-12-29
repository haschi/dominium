import { ChangeDetectionStrategy, Component, Input, TemplateRef } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-inventur-gruppe',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './gruppe.component.html',
    styleUrls: ['./gruppe.component.scss'],
})
export class GruppeComponent {

    @Input()
    public positionen: FormArray;

    @Input()
    public titel: TemplateRef<any>;

    hinzufuegen() {
        const waehrungsbetrag = new FormGroup({
            betrag: new FormControl('', Validators.required),
            waehrung: new FormControl('EUR', Validators.required)
        });

        const group = new FormGroup({
            position: new FormControl('', Validators.required),
            waehrungsbetrag: waehrungsbetrag,
        });

        this.positionen.push(group);
    }

    loeschen(index: number) {
        this.positionen.removeAt(index);
    }
}
