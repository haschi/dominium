import { Component, Input, OnInit } from '@angular/core';
import { Aktiva, Vermoegenswert } from '../bilanz.model';

@Component({
  selector: 'app-aktiva',
  templateUrl: './aktiva.component.html',
  styleUrls: ['./aktiva.component.scss']
})
export class AktivaComponent {

  @Input()
  aktiva: Aktiva

  get umlaufvermoegen(): Vermoegenswert[] {
    return this.aktiva.umlaufvermoegen
  }

  get anlagevermoegen(): Vermoegenswert[] {
    return this.aktiva.anlagevermoegen
  }

  get fehlbetrag(): Vermoegenswert[] {
    return this.aktiva.fehlbetrag
  }
}
