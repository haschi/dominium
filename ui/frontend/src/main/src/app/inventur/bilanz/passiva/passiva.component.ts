import { Component, Input, OnInit } from '@angular/core';
import { Passiva, Vermoegenswert } from '../bilanz.model';

@Component({
  selector: 'app-passiva',
  templateUrl: './passiva.component.html',
  styleUrls: ['./passiva.component.scss']
})
export class PassivaComponent {

  @Input()
  passiva: Passiva

  get eigenkapital(): Vermoegenswert[] {
    return this.passiva.eigenkapital
  }

  get fremdkapital(): Vermoegenswert[] {
    return this.passiva.fremdkapital
  }
}
