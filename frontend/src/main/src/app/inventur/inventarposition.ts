export interface Währungsbetrag {
    betrag: number;
    währung: string;
}

export interface Inventarposition {
    position: string;
    währungsbetrag: Währungsbetrag;
}
