import { AnyAction } from 'redux';
import { PositionEingabe } from './inventarposition';

export interface Eingabe {
    gruppe: string,
    kategorie: number;
    position: PositionEingabe
}

export interface InventurEingabeState {
    eingaben: Eingabe[];
}

const INVENTUREINGABE_HINZUFÜGEN = 'INVENTUREINGABE_HINZUFÜGEN';
const INVENTUREINGABE_ENTFERNEN = 'INVENTUREINGABE_ENTFERNEN';
const INVENTUREINGABE_LEEREN = 'INVENTUREINGABE_LEEREN'
const INVENTUREINGABE_LEERE_ZEILE = 'INVENTUREINGABE_LEERE_ZEILE'


export const INVENTUREINGBAE_INITIAL_STATE: InventurEingabeState = {
    eingaben: []
}

export function eingabeHinzufügen(gruppe: string, kategorie: number, positionen: PositionEingabe): AnyAction {
    return {
        type: INVENTUREINGABE_HINZUFÜGEN,
        payload: {
            gruppe: gruppe,
            kategorie: kategorie,
            positionen: positionen
        }
    }
}

export function zeileHinzufügen(gruppe: string, kategorie: number): AnyAction {
    return {
        type: INVENTUREINGABE_LEERE_ZEILE,
        payload: {
            gruppe: gruppe,
            kategorie: kategorie
        }
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
            console.info(`INVENTUREINGABE HINZUFÜGEN: ${JSON.stringify(action)}`)
            const neu = {
                    gruppe: action.payload.gruppe,
                    kategorie: action.payload.kategorie,
                    position: action.payload.positionen};

            const eingaben: Eingabe[] = Object.assign([], state.eingaben).concat(neu);

            return {eingaben}
        }

        case INVENTUREINGABE_LEERE_ZEILE: {
            return state;
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