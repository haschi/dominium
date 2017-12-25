import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { LoggerService } from '../shared/logger.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';

import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Inventar } from './inventar';
import { Router } from '@angular/router';

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

    private inventar$: Observable<Inventar>;

    constructor(private builder: FormBuilder,
                private log: LoggerService,
                private router: Router,
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

    getInventar(inventarId: string): Observable<Inventar> {
        return this.http.get<Inventar>('/gateway/inventur/' + inventarId)
    }

    postInventar(inventar: Inventar): Observable<string> {
        return this.http.post('/gateway/inventur', null, {
            observe: 'response',
            responseType: 'text'
        })
            .map((response: HttpResponse<string>) => {
                return response.headers.get('Aggregatkennung')
            });
    }

    speichern() {
        this.model = this.inventur.value;
        this.log.log('Inventur beginnen');
        this.log.log(JSON.stringify(this.inventur.value));

        console.info("speichern");

        // this.inventur$
        let aggregatkennung$: Observable<string> =
            this.http.post('/gateway/inventur', null, {observe: 'response', responseType: 'text'})
                .map((response: HttpResponse<string>) => {
                    return response.headers.get('Aggregatkennung')
                });

        aggregatkennung$.subscribe(aggregatkennung => this.router.navigate(['/inventur', {id: aggregatkennung}]))

        // let inventur$ = aggregatkennung$.flatMap(aggregatkennung =>
        //     this.http.get<Inventar>('/gateway/inventur/' + aggregatkennung)
        // )
        //
        // return this.inventur$;
        // response$.subscribe(val => console.info("RESPONSE:", val))

        //     // this.http.post(response.headers.get('location'), this.inventur.value)
        // .flatMap((aggregatkennung: string) => this.http.get('/gateway/inventur/' + aggregatkennung))
        // .map((response : HttpResponse<any>) => response.body)
        // .subscribe(
        //     (reply ) => {
        //         this.log.log('REPLY: ' + JSON.stringify(reply));
        //     },
        //     error => {
        //         this.log.error('ERROR: ' + JSON.stringify(error));
        //     });
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
