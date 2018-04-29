import { Injectable } from '@angular/core';
import { logger } from 'codelyzer/util/logger';
import { Observable } from 'rxjs/Observable';
import { NgRedux, select } from '@angular-redux/store';
import { CommandGatewayService } from '../shared/command-gateway/command-gateway.service';
import { QueryGatewayService } from '../shared/query-gateway/query-gateway.service';
import { QueryType } from './query-type';
import { ResultType } from './result-type';
import { Inventar } from './inventar';
import { AppState, InventurState } from '../store/model';
import { CommandType } from './command-type';
import { IdGeneratorService } from '../shared/command-gateway/id-generator.service';
import { Router } from '@angular/router';

@Injectable()
export class InventurService {

    @select(['inventur', 'inventurId'])
    inventurid$: Observable<string>;

    @select(['inventur', 'inventar'])
    inventar$: Observable<Inventar>;

    @select(['inventur'])
    state$: Observable<InventurState>;

    constructor(
        private idGenerator: IdGeneratorService,
        private gateway: CommandGatewayService,
        private queryGateway: QueryGatewayService,
        private router: Router,
        private store: NgRedux<AppState>) {

        this.inventurid$
            .filter(id => id != '')
            .subscribe(id => {
                this.router.navigate(['inventur', id])
            });
    }

    beginneInventur(id: any) {
        this.gateway.send(
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
        this.gateway.send(
            CommandType.BeendeInventur,
            {von: id},
            {})

        this.router.navigate(['/inventur/bilanz', id])
    }

    erfasseInventar(inventar: Inventar) {
        let id = this.store.getState().inventur.inventurId;

        this.gateway.send(
            CommandType.ErfasseInventar,
            {id: id, inventar: inventar},
            {}
        )
    }

    leseInventar() {
        let state = this.store.getState().inventur;
        let id = state.inventurId;

        console.info(`Inventur ID: ${id}`)
        this.queryGateway.send(
            QueryType.LeseInventar,
            {id: id},
            ResultType.Inventar);
    }
}
