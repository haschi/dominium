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

export function eingabeHinzufügen(gruppe: string, kategorie: number, positionen: PositionEingabe[]): AnyAction {
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

            const neu = action.payload.positionen.map(position => {
                return {
                    gruppe: action.payload.gruppe,
                    kategorie: action.payload.kategorie,
                    position: position}})

            const eingaben: Eingabe[] = state.eingaben
                .filter(e => !(e.gruppe === action.payload.gruppe && e.kategorie === action.payload.kategorie))
                .map(element => {return {gruppe: element.gruppe, kategorie: element.kategorie, position: element.position}})
                // ggf. map zum Klonen
                .concat(neu)

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