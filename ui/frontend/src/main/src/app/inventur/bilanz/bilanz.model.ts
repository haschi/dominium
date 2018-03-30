export interface Gruppe {
    buchstabe: string;
    bezeichnung: string
}

export interface Vermoegenswert {
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
}

export interface Passiva {
    eigenkapital: Vermoegenswert[];
    fremdkapital: Vermoegenswert[];
}

export interface Eroeffnungsbilanz {
    aktiva: Aktiva
    passiva: Passiva
    summe: string
}