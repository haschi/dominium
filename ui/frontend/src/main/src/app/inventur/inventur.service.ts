import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { NgRedux, select } from '@angular-redux/store';
import { CommandGatewayService } from '../shared/command-gateway/command-gateway.service';
import { Inventar } from './inventar';
import { AppState } from '../store/model';
import { CommandType } from './command-type';

@Injectable()
export class InventurService {

    @select(['inventur', 'inventurId'])
    inventurid$: Observable<string>;

    @select(['inventur', 'inventar'])
    inventar$: Observable<Inventar>;

    constructor(private gateway: CommandGatewayService, private store: NgRedux<AppState>) {
    }

    beginneInventur(id: any) {
        this.gateway.send(
            CommandType.BeginneInventur,
            {id: id},
            {});
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
