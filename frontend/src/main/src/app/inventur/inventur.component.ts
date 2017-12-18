import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { LoggerService } from '../shared/logger.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'app-inventur',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './inventur.component.html',
    styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit {

    private inventur: FormGroup;

    private model: any;

    private model$: Observable<any>;

    constructor(private builder: FormBuilder,
                private log: LoggerService,
                private http: HttpClient) {
    }

    ngOnInit() {
        this.inventur = this.builder.group({
            anlagevermoegen: this.builder.array([]),
            umlaufvermoegen: this.builder.array([]),
            schulden: this.builder.array([])
        });

        this.model$ = this.inventur.valueChanges;

        this.inventur.valueChanges.subscribe(
            value => this.log.log('Wertänderung: ' + JSON.stringify(value)));
    }

    speichern(): void {
        this.model = this.inventur.value;
        this.log.log('Inventur beginnen');
        this.log.log(JSON.stringify(this.inventur.value));

        this.http.post('http://localhost:4200/gateway/inventar', null,
            {observe: 'response', responseType: 'text'} )
            .flatMap((response: HttpResponse<string>) => this.http.post(response.headers.get('location'), this.inventur.value))
            .subscribe(
                (reply ) => {
                    this.log.log('REPLY: ' + JSON.stringify(reply));
                },
                error => {
                    this.log.error('ERROR: ' + JSON.stringify(error));
                });
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
