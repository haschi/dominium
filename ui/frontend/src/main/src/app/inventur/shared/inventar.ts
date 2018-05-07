import { Vermoegenswert } from '../bilanz/bilanz.model';
import { Inventarposition } from './inventarposition';

export interface Reinvermoegen {
    summeDesVermoegens: string;
    summeDerSchulden: string;
    reinvermoegen: string
}

export interface Inventar {
    erstelltAm: string,
    anlagevermoegen: Inventarposition[];
    umlaufvermoegen: Inventarposition[];
    schulden: Inventarposition[];
    reinvermoegen: Reinvermoegen;
}

export interface InventurEingabe
{
    anlagevermoegen: Vermoegenswert[],
    umlaufvermoegen: Vermoegenswert[],
    schulden: Vermoegenswert[]
}