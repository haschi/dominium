import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LoggerService } from '../shared/logger.service';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/mergeMap';
import { mergeMap } from 'rxjs/operator/mergeMap';

@Component({
    selector: 'app-inventur',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './inventur.component.html',
    styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit {

    private formGroup: FormGroup;

    private model: any;

    constructor(
        private builder: FormBuilder,
        private log: LoggerService,
        private http: Http) {
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
        this.log.log('Inventur beginnen');
        this.log.log(JSON.stringify(this.formGroup.value));

        const that = this;

        this.http.post('/api/inventar', {})
            .flatMap(response => this.http.post(response.headers.get('location'), this.formGroup.value))
            .subscribe(
                reply => {this.log.log('REPLY: ' + JSON.stringify(reply)); },
                error => {this.log.log('ERROR: ' + JSON.stringify(error)); });
    }

    inventarSpeichern(response: Response) {
        this.log.log('Inventar speichern');
        const location = response.headers.get('location');
        return this.http.post(location, this.formGroup.value);
    }
}
