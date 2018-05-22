
import {
    Component, Input
} from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import { Gruppe, Kategorie } from '../shared/gruppen.redux';

@Component({
    selector: 'app-inventur-gruppe',
    templateUrl: './gruppe.component.html',
    styleUrls: ['./gruppe.component.scss'],
})
export class GruppeComponent {

    @Input()
    public positionen: FormArray;

    loeschen(index: number) {
        this.positionen.removeAt(index);
    }
}
