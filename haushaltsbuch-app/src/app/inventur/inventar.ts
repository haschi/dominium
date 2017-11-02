import { Inventarposition } from './inventarposition';

export interface Inventar {
    anlagevermoegen: Inventarposition[];
    umlaufvermoegen: Inventarposition[];
    schulden: Inventarposition[];
}

export const kategorien = ['Anlagevermögen', 'Umlaufvermögen', 'Darlehen'];
