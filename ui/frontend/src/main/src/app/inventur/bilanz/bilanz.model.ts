export interface Gruppe {
    buchstabe: string;
    bezeichnung: string
}

export interface Vermoegenswert {
    kategorie: string,
    position: string;
    waehrungsbetrag: string
}

export interface Bilanzgruppe {
    gruppe: Gruppe;
    vermoegenswerte: Vermoegenswert[]
}

export interface Aktiva {
    anlagevermoegen: Vermoegenswert[];
    umlaufvermoegen: Vermoegenswert[];
    fehlbetrag: Vermoegenswert[];
    summe: string
}

export interface Passiva {
    eigenkapital: Vermoegenswert[];
    fremdkapital: Vermoegenswert[];
    summe: string
}

export interface Eroeffnungsbilanz {
    aktiva: Aktiva
    passiva: Passiva
}