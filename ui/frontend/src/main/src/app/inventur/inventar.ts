import { Inventarposition } from './inventarposition';

export interface Reinvermoegen {
    summeDesVermoegens: string;
    summeDerSchulden: string;
    reinvermoegen: string
}

export interface Inventar {
    anlagevermoegen: Inventarposition[];
    umlaufvermoegen: Inventarposition[];
    schulden: Inventarposition[];
    reinvermoegen: Reinvermoegen;
}
