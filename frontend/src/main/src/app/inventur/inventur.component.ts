import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { LoggerService } from '../shared/logger.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';

import { Observable } from 'rxjs/Observable';
import { Inventar } from './inventar';
import { Router } from '@angular/router';
import { InventurService } from './inventur.service';

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
                private inventurService: InventurService) {
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

    speichern() {
        this.model = this.inventur.value;
        this.log.log('Inventur beginnen');
        this.log.log(JSON.stringify(this.inventur.value));

        console.info("speichern");

        this.inventurService.erfasseInventar(this.inventur.value);
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
