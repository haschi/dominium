import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-inventur',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './inventur.component.html',
    styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit {

    private formGroup: FormGroup;
    private model: any;
    constructor(private builder: FormBuilder, private http: HttpClient) {
    }

    ngOnInit() {
        this.formGroup = this.builder.group({
            anlagevermoegen: this.builder.array([]),
            umlaufvermoegen: this.builder.array([]),
            schulden: this.builder.array([])
        });
    }

    speichern(): void {
        this.model = this.formGroup.value;
        console.info('Daten werden gespeichert');

        this.http
            .post('/api/inventur', this.formGroup.value)
            .subscribe();
    }
}
