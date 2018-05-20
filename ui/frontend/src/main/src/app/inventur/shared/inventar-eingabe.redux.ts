import { AnyAction } from 'redux';
import { Inventarposition } from './inventarposition';

export interface InventurEingabeState {
    eingaben: Inventarposition[];
}

const INVENTUREINGABE_HINZUFÜGEN = 'INVENTUREINGABE_HINZUFÜGEN';
const INVENTUREINGABE_ENTFERNEN = 'INVENTUREINGABE_ENTFERNEN';
const INVENTUREINGABE_LEEREN = 'INVENTUREINGABE_LEEREN'

export const INVENTUREINGBAE_INITIAL_STATE: InventurEingabeState = {
    eingaben: []
}

export function eingabeHinzufügen(position: Inventarposition[]): AnyAction {
    return {
        type: INVENTUREINGABE_HINZUFÜGEN,
        payload: position
    }
}

export function eingabeEntfernen(index: number): AnyAction {
    return {
        type: INVENTUREINGABE_ENTFERNEN,
        payload: index
    }
}

export function eingabeLeeren(): AnyAction {
    return {
        type: INVENTUREINGABE_LEEREN
    }
}

export function inventureingabe(state: InventurEingabeState = INVENTUREINGBAE_INITIAL_STATE, action: AnyAction): InventurEingabeState {
    switch (action.type) {
        case INVENTUREINGABE_HINZUFÜGEN: {
            return {eingaben: [...state.eingaben, ...action.payload]}
        }

        case  INVENTUREINGABE_ENTFERNEN: {
            return {eingaben: state.eingaben.filter((value, index) => index !== action.payload)}
        }

        case INVENTUREINGABE_LEEREN: {
            return {eingaben: []}
        }

        default:
            return state;
    }
}