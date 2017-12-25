import { Injectable } from '@angular/core';
import { Action } from 'redux';
import { Inventar } from '../inventur/inventar';

@Injectable()
export class ActionsService {

    static InventurBegonnen = 'Inventur begonnen';
    static InventarErfasst = 'Inventar erfasst';

    constructor() {
    }

    begonnen(inventarId: string): Action & { id: string } {
        return {type: ActionsService.InventurBegonnen, id: inventarId}
    }

    erfasst(inventar: Inventar): Action & { inventar: Inventar } {
        return {type: ActionsService.InventarErfasst, inventar: inventar}
    }
}
