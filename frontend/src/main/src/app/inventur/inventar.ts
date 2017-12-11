import { Inventarposition } from './inventarposition';

export interface Inventar {
    anlagevermoegen: Inventarposition[];
    umlaufvermoegen: Inventarposition[];
    schulden: Inventarposition[];
}
