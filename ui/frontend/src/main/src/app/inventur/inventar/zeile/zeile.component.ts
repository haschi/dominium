import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-zeile',
  templateUrl: './zeile.component.html',
  styleUrls: ['./zeile.component.scss']
})
export class ZeileComponent {

  @Input() text: string;
  @Input() betrag: string;
  @Input() summe: string;

  get zwischensumme(): boolean {
    return this.summe === 'zwischensumme';
  }

  get gesamtsumme(): boolean {
    return this.summe === 'gesamtsumme';
  }
}
