import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-position',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './position.component.html',
    styleUrls: ['./position.component.scss']
})
export class PositionComponent {

    options = {
        prefix: '',
        suffix: ' EUR',
        thousands: '.',
        decimal: ',',
        allowNegative: false
    };

    @Input()
    public parent: FormGroup;

    @Input()
    public arrayName: string;

    get controls(): FormArray {
        return this.parent.get(this.arrayName) as FormArray;
    }

    hinzufuegen() {
        this.controls.push(
            new FormGroup({
                position: new FormControl('', Validators.required),
                betrag: new FormControl('', Validators.required)
            }));
    }

    loeschen(index: number) {
        this.controls.removeAt(index);
    }
}
