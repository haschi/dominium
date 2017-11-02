import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { Inventar } from './inventar';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-inventur',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './inventur.component.html',
    styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit {
    private ergebnis: any;
    private formGroup: FormGroup;
    private model: Inventar = {
        anlagevermoegen: [],
        umlaufvermoegen: [],
        schulden: []
    };

    constructor(private builder: FormBuilder) {
    }

    ngOnInit() {
        this.formGroup = this.builder.group({
            anlagevermoegen: this.builder.array([]),
            umlaufvermoegen: this.builder.array([]),
            schulden: this.builder.array([])
        });
    }
}
