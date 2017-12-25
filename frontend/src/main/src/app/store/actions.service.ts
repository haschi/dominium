import { Injectable } from '@angular/core';
import { Action } from 'redux';
import { Inventar } from '../inventur/inventar';
import { dispatch } from '@angular-redux/store';

export enum Actions {
    InventurBegonnen,
    InventarErfasst
}

@Injectable()
export class ActionsService {

    constructor() {
    }

    @dispatch()
    begonnen = (inventarId: string): Action & { id: string } => ({
        type: Actions.InventurBegonnen, id: inventarId
    });

    @dispatch()
    erfasst(inventar: Inventar): Action & { inventar: Inventar } {
        return {type: Actions.InventarErfasst, inventar: inventar}
    }
}
