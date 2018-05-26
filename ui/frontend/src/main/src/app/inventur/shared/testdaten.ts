import { GruppenState } from './gruppen.redux';

export const state: GruppenState = {
    gruppen: {
        anlagevermoegen: {bezeichnung: 'Anlagevermögen', kategorien: []},
        umlaufvermoegen: {bezeichnung: 'Umlaufvermögen', kategorien: []},
        schulden: {bezeichnung: 'Schulden', kategorien: []}
    }
}

export const testgruppen: GruppenState = {
    gruppen: {
        anlagevermoegen: {bezeichnung: 'Anlagevermögen', kategorien: [{kategorie: 'Aktien'}, {kategorie: 'Lebensversicherung'}, {kategorie: 'Rentenfonds'}]},
        umlaufvermoegen: {bezeichnung: 'Umlaufvermögen', kategorien: [{kategorie: 'Girokonto'}, {kategorie: 'Geld-Börse'}, {kategorie: 'Sparbuch'}]},
        schulden: {bezeichnung: 'Schulden', kategorien: [{kategorie: 'Verbraucherkredite'}, {kategorie: 'Hypotheken'}]}
    }
}