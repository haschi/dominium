import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { NgRedux, select } from '@angular-redux/store';
import { CommandGatewayService } from '../shared/command-gateway/command-gateway.service';
import { Inventar } from './inventar';
import { AppState } from '../store/model';
import { CommandType } from './command-type';
import { IdGeneratorService } from '../shared/command-gateway/id-generator.service';
import { Router } from '@angular/router';

@Injectable()
export class InventurService {

    @select(['inventur', 'inventurId'])
    inventurid$: Observable<string>;

    @select(['inventur', 'inventar'])
    inventar$: Observable<Inventar>;

    constructor(
        private idGenerator: IdGeneratorService,
        private gateway: CommandGatewayService,
        private router: Router,
        private store: NgRedux<AppState>) {

        this.inventurid$
            .filter(id => id != '')
            .subscribe(id => this.router.navigate(['inventur', id]));
    }

    beginneInventur(id: any) {
        this.gateway.send(
            CommandType.BeginneInventur,
            {id: id},
            {});
    }

    beginnen(): any {
        let id = this.idGenerator.neu();
        this.beginneInventur(id)
        return id;
    }

    erfasseInventar(inventar: Inventar) {
        let id = this.store.getState().inventur.inventurId;

        this.gateway.send(
            CommandType.ErfasseInventar,
            {id: id, inventar: inventar},
            {}
        )
    }
}
