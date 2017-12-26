import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { NgRedux, select } from '@angular-redux/store';
import { CommandGatewayService } from '../shared/command-gateway/command-gateway.service';
import { Inventar } from './inventar';
import { AppState } from '../store/model';

@Injectable()
export class InventurService {

    @select(['inventur', 'inventurId'])
    inventurid$: Observable<string>;

    @select(['inventur', 'inventar'])
    inventar$: Observable<Inventar>;

    constructor(private gateway: CommandGatewayService, private store: NgRedux<AppState>) {
    }

    beginneInventur() {
        this.gateway.send(
            'com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur',
            {id: '12345'},
            {});
    }

    erfasseInventar(inventar: Inventar) {
        let id = this.store.getState().inventur.inventurId;

        this.gateway.send(
            'com.github.haschi.dominium.haushaltsbuch.core.model.commands.ErfasseInventar',
            {id: id, inventar: inventar},
            {}
        )
    }
}
