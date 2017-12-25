import { Injectable } from '@angular/core';
import { Action } from 'redux';
import { Inventar } from '../inventur/inventar';

export enum Actions {
    InventurBegonnen,
    InventarErfasst
}

@Injectable()
export class ActionsService {

    constructor() {
    }

    begonnen(inventarId: string): Action & { id: string } {
        return {type: Actions.InventurBegonnen, id: inventarId}
    }

    erfasst(inventar: Inventar): Action & { inventar: Inventar } {
        return {type: Actions.InventarErfasst, inventar: inventar}
    }
}
