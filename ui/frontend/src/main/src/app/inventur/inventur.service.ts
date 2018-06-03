import { Injectable, OnInit } from '@angular/core';
import { el } from '@angular/platform-browser/testing/src/browser_util';
import { logger } from 'codelyzer/util/logger';
import { Observable } from 'rxjs/Observable';
import { NgRedux, select } from '@angular-redux/store';
import { CommandGatewayService } from '../shared/command-gateway/command-gateway.service';
import { QueryGatewayService } from '../shared/query-gateway/query-gateway.service';
import {
    GruppenState,
    InventurGruppe,
    INVENTURGRUPPEN_INITIAL_STATE
} from './shared/gruppen.redux';
import { Eingabe } from './shared/inventar-eingabe.redux';
import { InventurState } from './shared/inventur.redux';
import { QueryType } from './shared/query-type';
import { ResultType } from './shared/result-type';
import { Inventar, InventurEingabe } from './shared/inventar';
import { AppState} from '../store/model';
import { CommandType } from './shared/command-type';
import { IdGeneratorService } from '../shared/command-gateway/id-generator.service';
import { Router } from '@angular/router';

interface LeseInventar {id: string}

// TODO: Unterscheiden zwischen Inventar und Inventur. Ggf. 2 Services?
@Injectable()
export class InventurService implements OnInit{

    @select(['inventur', 'inventurId'])
    inventurid$: Observable<string>;

    @select(['inventur', 'inventar'])
    inventar$: Observable<Inventar>;

    @select(['inventur'])
    state$: Observable<InventurState>;

    @select(['inventurGruppen', 'gruppen'])
    gruppen$: Observable<InventurGruppe>


    public static get leereGruppen(): InventurGruppe {
        return INVENTURGRUPPEN_INITIAL_STATE.gruppen
    }

    constructor(
        private idGenerator: IdGeneratorService,
        private command: CommandGatewayService,
        private query: QueryGatewayService,
        private router: Router,
        private store: NgRedux<AppState>) {

        this.inventurid$
            .filter(id => id != '')
            .subscribe(id => {
                this.router.navigate(['inventur', id])
            });

        this.leseInventurGruppen();
    }

    eingabe$(gruppe: string, kategorie: number): Observable<Eingabe[]> {
        //console.info(`Eingabe: ${gruppe}, ${kategorie}`)
        return this.store.select(s => s.inventureingabe.eingaben)
          //  .do(eingabe => console.info(`Alle: ${JSON.stringify(eingabe)}`))
            .map(eingabe => eingabe.filter(element => element.gruppe === gruppe && element.kategorie === kategorie))
          //  .do(eingabe => console.info(`Gefiltert: ${JSON.stringify(eingabe)}`))
    }

    beginneInventur(id: any) {
        this.command.send(
            CommandType.BeginneInventur,
            {id: id},
            {});

        const state = this.store.getState();
    }

    beginnen(): any {
        let id = this.idGenerator.neu();
        this.beginneInventur(id);
        return id;
    }

    beenden(id: any): void {
        this.command.send(
            CommandType.BeendeInventur,
            {von: id},
            {})

        this.router.navigate(['/inventur/bilanz', id])
    }

    erfasseInventar(eingabe: InventurEingabe) {
        let id = this.store.getState().inventur.inventurId;

        this.command.send(
            CommandType.ErfasseInventar,
            {id: id, ...eingabe},
            {}
        )
    }

    leseInventar() {
        let state = this.store.getState().inventur;
        let id = state.inventurId;

        this.query.send(
            QueryType.LeseInventar,
            {id: id},
            ResultType.Inventar);
    }

    leseInventurGruppen() {
        this.query.send(
            QueryType.LeseInventurGruppen,
            {},
            ResultType.InventurGruppe
        )
    }

    ngOnInit(): void {
        console.info("Inventur Service onInit")

    }
}
