export interface Inventarposition {
    kategorie: string;
    position: string;
    waehrungsbetrag: string;
}

export interface Waehrungsbetrag {
    betrag: string;
    waehrung: string;
}
export interface PositionEingabe {
    position: string;
    waehrungsbetrag: Waehrungsbetrag;
}